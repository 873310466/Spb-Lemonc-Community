package com.lemonfish.mapper;

import com.lemonfish.entity.CommentLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  * 评论点赞表 Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@Repository
@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

    @Delete("delete from comment_like where comment_id=#{commentId} and user_id =#{userId}")
    boolean cancel(CommentLike commentLike);

}
