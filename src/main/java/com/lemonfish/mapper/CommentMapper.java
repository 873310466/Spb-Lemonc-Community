package com.lemonfish.mapper;

import com.lemonfish.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
  * 评论表 Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-08
  */
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Update("update comment set like_count = like_count - 1 where id = #{commentId}")
    boolean decrLikeCount(Long commentId);

    @Update("update comment set like_count = like_count + 1 where id = #{commentId}")
    boolean incrLikeCount(Long commentId);
}
