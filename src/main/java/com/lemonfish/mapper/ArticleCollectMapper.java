package com.lemonfish.mapper;

import com.lemonfish.entity.ArticleCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemonfish.entity.ArticleLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  *  Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-12
  */
@Repository
@Mapper
public interface ArticleCollectMapper extends BaseMapper<ArticleCollect> {

    /**
     * 取消收藏
     * @param articleCollect
     * @return
     */
    @Delete("delete from article_collect where article_id=#{articleId} and user_id =#{userId}")
    Boolean cancel(ArticleCollect articleCollect);
}
