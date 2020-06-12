package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.ArticleLikeDTO;
import com.lemonfish.dto.CommentLikeDTO;
import com.lemonfish.entity.ArticleLike;
import com.lemonfish.entity.CommentLike;
import com.lemonfish.entity.Notification;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.enumcode.NotificationActionEnum;
import com.lemonfish.enumcode.NotificationTypeEnum;
import com.lemonfish.exception.BaseException;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.CommentLikeMapper;
import com.lemonfish.mapper.CommentMapper;
import com.lemonfish.mapper.NotificationMapper;
import com.lemonfish.service.CommentLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.ValidateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
  * <p>
  * 评论点赞表 服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@Service
@Transactional
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {


    @Autowired
    private CommentLikeMapper commentLikeMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public boolean thumbUpComment(CommentLikeDTO commentLike) {
        if (!commentLike.getReceiverId().equals(commentLike.getUserId())) {
            notifyThumbUp(commentLike);
        }
        return commentLikeMapper.insert(commentLike) > 0 && commentMapper.incrLikeCount(commentLike.getCommentId());
    }

    @Override
    public boolean cancelThumbUpComment(CommentLike commentLike) {
        return commentLikeMapper.cancel(commentLike)  && commentMapper.decrLikeCount(commentLike.getCommentId());

    }

    @Override
    public boolean isThumbUp(Long cid, Long uid) {
        LambdaQueryWrapper<CommentLike> lqw = new LambdaQueryWrapper<>();
        lqw
                .eq(CommentLike::getCommentId, cid)
                .eq(CommentLike::getUserId, uid);
        Integer res = commentLikeMapper.selectCount(lqw);
        return res>0;
    }

    @Override
    public Set<Long> getCommentIDSet(Long aid, Long uid) {
        // 校验ID
        ValidateUtils.V_AIDandUID(aid, uid);
        // 条件匹配
        LambdaQueryWrapper<CommentLike> lqw = new LambdaQueryWrapper<>();
        lqw
                .eq(CommentLike::getArticleId, aid)
                .eq(CommentLike::getUserId, uid);
        List<CommentLike> commentLikes = commentLikeMapper.selectList(lqw);

        return commentLikes.stream().map(CommentLike::getCommentId).collect(Collectors.toSet());
    }

    /**
     * 点赞别人需要通知对方
     * @param commentLike
     */
    private void notifyThumbUp(CommentLikeDTO commentLike) {
        // 1.创建通知
        Notification notification = new Notification();
        notification
                .setNotifierId(commentLike.getUserId())
                .setNotifyAction(NotificationActionEnum.THUMB_UP.getAction())
                .setReceiverId(commentLike.getReceiverId())
                .setTargetId(commentLike.getArticleId())
                .setTargetTitle(commentLike.getCommentContent())
                .setTargetType(NotificationTypeEnum.NOTIFY_COMMENT.getType());
        notificationMapper.insert(notification);
    }

}


