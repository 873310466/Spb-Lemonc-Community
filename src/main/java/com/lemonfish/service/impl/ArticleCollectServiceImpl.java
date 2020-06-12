package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.ArticleCollectCancelDTO;
import com.lemonfish.dto.ArticleCollectDTO;
import com.lemonfish.entity.ArticleCollect;
import com.lemonfish.entity.Notification;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.enumcode.NotificationActionEnum;
import com.lemonfish.enumcode.NotificationTypeEnum;
import com.lemonfish.mapper.ArticleCollectMapper;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.CollectionMapper;
import com.lemonfish.mapper.NotificationMapper;
import com.lemonfish.service.ArticleCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.ValidateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;
import java.util.stream.Collectors;

/**
 *  * <p>
 *  *  服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-12
 *  
 */
@Service
@Transactional
public class ArticleCollectServiceImpl extends ServiceImpl<ArticleCollectMapper, ArticleCollect> implements ArticleCollectService {


    @Autowired
    private ArticleCollectMapper articleCollectMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Boolean collectArticle(ArticleCollectDTO articleCollectDTO) {
        // 收藏别人需要通知对方
        if (!articleCollectDTO.getAuthorId().equals(articleCollectDTO.getUserId())) {
            notifyCollect(articleCollectDTO);
        }
        // 榜单+1
        redisUtil.zIncrScore(
                InfoEnum.COLLECTION_LIST.getName(),
                InfoEnum.USER_ID_PREFIX.getName() + articleCollectDTO.getAuthorId(),
                1
        );
        Set<ArticleCollect> binds = articleCollectDTO.getCollectionsId().stream().map(item -> new ArticleCollect(articleCollectDTO.getArticleId(), articleCollectDTO.getUserId(), item)).collect(Collectors.toSet());
        return this.saveBatch(binds) && articleMapper.incrCollectComment(articleCollectDTO.getArticleId()) && collectionMapper.incrCount(articleCollectDTO.getCollectionsId());

    }

    private void notifyCollect(ArticleCollectDTO articleCollect) {
// 1.创建通知
        Notification notification = new Notification();
        notification
                .setNotifierId(articleCollect.getUserId())
                .setNotifyAction(NotificationActionEnum.COLLECT.getAction())
                .setReceiverId(articleCollect.getAuthorId())
                .setTargetId(articleCollect.getArticleId())
                .setTargetTitle(articleCollect.getArticleTitle())
                .setTargetType(NotificationTypeEnum.NOTIFY_ARTICLE.getType());
        notificationMapper.insert(notification);
    }

    @Override
    public Boolean cancelCollectArticle(Long aid, Long uid) {
        // 榜单-1
        redisUtil.zIncrScore(
                InfoEnum.COLLECTION_LIST.getName(),
                InfoEnum.USER_ID_PREFIX.getName() + uid,
                -1
        );
        return articleMapper.decrOneCollectCount(aid);
    }

    @Override
    public Integer isCollected(Long aid, Long uid) {
        LambdaQueryWrapper<ArticleCollect> lqw = new LambdaQueryWrapper<>();
        lqw
                .eq(ArticleCollect::getArticleId, aid)
                .eq(ArticleCollect::getUserId, uid);
        return articleCollectMapper.selectCount(lqw);
    }

    /**
     * 取消 1 文章 -> n 收藏夹 联系
     * @param articleCollect
     * @return
     */
    @Override
    public Boolean cancelCollectionInsideArticle(ArticleCollectDTO articleCollect) {
        int delete = articleCollectMapper.delete(
                new LambdaQueryWrapper<ArticleCollect>()
                        .eq(ArticleCollect::getArticleId, articleCollect.getArticleId())
                        .in(ArticleCollect::getCollectionId, articleCollect.getCollectionsId()));
        return collectionMapper.decrCount(articleCollect.getCollectionsId()) && delete > 0;
    }

    /**
     * 取消 1 收藏夹 -> n 文章 联系
     *
     * @param articleCollect
     * @return
     */
    @Override
    public Boolean cancelArticleInsideCollection(ArticleCollectCancelDTO articleCollect) {
        ValidateUtils.validateId(articleCollect.getCollectionId());
        ValidateUtils.V_COLLECTION(articleCollect.getArticlesId());
        // 1. 删除文章-收藏夹的联系
        int delete = articleCollectMapper.delete(
                new LambdaQueryWrapper<ArticleCollect>()
                        .in(ArticleCollect::getArticleId, articleCollect.getArticlesId())
                        .eq(ArticleCollect::getCollectionId, articleCollect.getCollectionId()));

        // 2. 减少收藏夹里面的文章数量count
        // delete 是多少 就代表删除了几个
        boolean res1 = collectionMapper.decrOneCount(articleCollect.getCollectionId(), delete);

        // 3. 减少文章的被收藏数
        boolean res2 = articleMapper.decrMulCollectCount(articleCollect.getArticlesId());
        return res1 && res2 && delete > 0;
    }
}


