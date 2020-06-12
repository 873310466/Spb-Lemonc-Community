package com.lemonfish.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemonfish.entity.Article;
import com.lemonfish.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  * 收藏集表 服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-13
  */
public interface CollectionService extends IService<Collection> {


    List<Collection> getUserCollection(Long id);

    /**
     * 当用户想要取消收藏，先获取收藏当前文章的收藏夹
     * @param articleId
     * @param userId
     * @return
     */
    List<Collection> getUserHasCollection(Long articleId,Long userId);

    Collection getCollectionById(Long cid);


    /**
     * 删除收藏夹以及 文章-收藏表取消联系，文章收藏数-1
     * @param cid
     * @return
     */
    boolean deleteCollection(Long cid);

    boolean updateOne(Collection collection);
}
