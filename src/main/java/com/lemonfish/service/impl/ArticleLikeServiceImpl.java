package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.ArticleLikeDTO;
import com.lemonfish.entity.ArticleLike;
import com.lemonfish.entity.Notification;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.enumcode.NotificationActionEnum;
import com.lemonfish.enumcode.NotificationTypeEnum;
import com.lemonfish.mapper.ArticleLikeMapper;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.NotificationMapper;
import com.lemonfish.service.ArticleLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *  * <p>
 *  * 文章点赞表 服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-11
 *  
 */
@Service
@Transactional
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {


    @Autowired
    private ArticleLikeMapper articleLikeMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 点赞
     */
    @Override
    public boolean thumbUpArticle(ArticleLikeDTO articleLike) {
        // 点赞别人需要通知对方
        if (!articleLike.getUserId().equals(articleLike.getReceiverId())) {
            notifyThumbUp(articleLike);
        }
        // 榜单+1
        redisUtil.zIncrScore(
                InfoEnum.LIKE_LIST.getName(),
                InfoEnum.USER_ID_PREFIX.getName() + articleLike.getUserId(),
                1
        );
        // 点赞数+1 且 用户-文章 点赞联系表
        return articleLikeMapper.insert(articleLike) > 0 && articleMapper.incrLikeCount(articleLike.getArticleId());
    }

    /**
     * 点赞别人需要通知对方
     * @param articleLike
     */
    private void notifyThumbUp(ArticleLikeDTO articleLike) {
        // 1.创建通知
        Notification notification = new Notification();
        notification
                .setNotifierId(articleLike.getUserId())
                .setNotifyAction(NotificationActionEnum.THUMB_UP.getAction())
                .setReceiverId(articleLike.getReceiverId())
                .setTargetId(articleLike.getArticleId())
                .setTargetTitle(articleLike.getArticleTitle())
                .setTargetType(NotificationTypeEnum.NOTIFY_ARTICLE.getType());
        notificationMapper.insert(notification);
    }

    /**
     * 取消点赞
     */
    @Override
    public boolean cancelThumbUpArticle(ArticleLike articleLike) {
        // 榜单-1
        redisUtil.zIncrScore(
                InfoEnum.LIKE_LIST.getName(),
                InfoEnum.USER_ID_PREFIX.getName() + articleLike.getUserId(),
                -1
        );
        return articleLikeMapper.cancel(articleLike)  && articleMapper.decrLikeCount(articleLike.getArticleId());
    }

    /**
     * 是否点赞
     */
    @Override
    public boolean isThumbUp(Long aid, Long uid) {
        LambdaQueryWrapper<ArticleLike> lqw = new LambdaQueryWrapper<>();
        lqw
                .eq(ArticleLike::getArticleId, aid)
                .eq(ArticleLike::getUserId, uid);
        Integer res = articleLikeMapper.selectCount(lqw);
        return res>0;
    }
}


