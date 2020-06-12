package com.lemonfish.mapper;

import com.lemonfish.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * 标签表 Mapper 接口
  *
  * @author LemonFish
  * @since 2020-05-07
  */
@Repository
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    @Select("select * from tag t left join article_tag at on t.id = at.tag_id where at.article_id=#{articleId}")
    @Results(
            @Result(id=true,property = "id",column = "id")
    )
    @Cacheable("getTagsById")
    List<Tag> getTagsById(@Param("articleId") Long articleId);


    /**
     * 通过文章ID获取对应的TagID
     */
    @Select("select at.tag_id from article_tag at where at.article_id = #{id}" )
    List<Long> getArticlesIdByTagId(@Param("id") Long articleId);
}
