package com.lemonfish.service;

import com.lemonfish.dto.ArticleLikeDTO;
import com.lemonfish.entity.ArticleLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 ** 文章点赞表 服务类
 ** @author LemonFish
 ** @since 2020-05-11
 */
public interface ArticleLikeService extends IService<ArticleLike> {
    /**
     * 点赞文章
     * @return boolean
     */
    boolean thumbUpArticle(ArticleLikeDTO articleLike);

    /**
     * 取消点赞
     * @param articleLike
     */
    boolean cancelThumbUpArticle(ArticleLike articleLike);

    /**
     * 判断是否点赞
     * @param aid
     * @param uid
     * @return boolean
     */
    boolean isThumbUp(Long aid, Long uid);

}
