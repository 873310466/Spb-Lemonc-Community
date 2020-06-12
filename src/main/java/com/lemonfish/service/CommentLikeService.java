package com.lemonfish.service;

import com.lemonfish.dto.CommentLikeDTO;
import com.lemonfish.entity.CommentLike;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
  * <p>
  * 评论点赞表 服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-11
  */
public interface CommentLikeService extends IService<CommentLike> {


    boolean thumbUpComment(CommentLikeDTO commentLike);

    boolean cancelThumbUpComment(CommentLike commentLike);

    boolean isThumbUp(Long cid, Long uid);

    Set<Long> getCommentIDSet(Long aid, Long uid);
}
