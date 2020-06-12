package com.lemonfish.service;

import com.lemonfish.dto.ArticleCollectCancelDTO;
import com.lemonfish.dto.ArticleCollectDTO;
import com.lemonfish.entity.ArticleCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
  * <p>
  *  服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-12
  */
public interface ArticleCollectService extends IService<ArticleCollect> {


    Boolean collectArticle(ArticleCollectDTO articleLike);

    /**
     * 减少文章的收藏数
     * @param aid
     * @param uid
     * @return
     */
    Boolean cancelCollectArticle(Long aid,Long uid);

    /**
     * 判断用户是否收藏该文章
     * @param aid
     * @param uid
     * @return
     */
    Integer isCollected(Long aid, Long uid);

    /**
     * 取消 1 文章 -> n 收藏夹 联系
     * @param articleCollect
     * @return
     */
    Boolean cancelCollectionInsideArticle(ArticleCollectDTO articleCollect);

    /**
     * 取消 1 收藏夹 -> n 文章 联系
     * @param articleCollect
     * @return
     */
    Boolean cancelArticleInsideCollection(ArticleCollectCancelDTO articleCollect);
}
