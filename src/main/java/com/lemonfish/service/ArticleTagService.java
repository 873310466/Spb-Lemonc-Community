package com.lemonfish.service;

import com.lemonfish.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  * 文章与标签的中间表 服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-07
  */
public interface ArticleTagService extends IService<ArticleTag> {
    List<ArticleTag> selectArticleTagList();

    ArticleTag selectArticleTagById(Long id);

    int updateArticleTag(ArticleTag articleTag);

    int  deleteArticleTagById(Long id);

}
