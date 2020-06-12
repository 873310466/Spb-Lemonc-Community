package com.lemonfish.service;

import com.lemonfish.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  * 评论表 服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-08
  */
public interface CommentService extends IService<Comment> {

    boolean saveComment(Comment comment);

    /**
     * 通过文章ID 获取 相应的评论
     * @param id
     * @return
     */
    List<Comment> getComments(Long id);

}
