package com.lemonfish.mapper;

import com.lemonfish.entity.ArticleLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  * 文章点赞表 Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@Repository
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

    @Delete("delete from article_like where article_id=#{articleId} and user_id =#{userId}")
    boolean cancel(ArticleLike articleLike);
}
