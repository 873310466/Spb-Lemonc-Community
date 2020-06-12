package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemonfish.entity.Article;
import com.lemonfish.entity.ArticleCollect;
import com.lemonfish.entity.Collection;
import com.lemonfish.mapper.ArticleCollectMapper;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.CollectionMapper;
import com.lemonfish.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.ValidateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  * <p>
 *  * 收藏集表 服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-13
 *  
 */
@Service
@Transactional
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ArticleCollectMapper articleCollectMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Collection> getUserCollection(Long id) {
        ValidateUtils.validateId(id);
        return collectionMapper.selectList(new LambdaQueryWrapper<Collection>().eq(Collection::getUserId, id));
    }

    @Override
    public List<Collection> getUserHasCollection(Long articleId, Long userId) {
        ValidateUtils.validateId(articleId);
        // 这里可以连表查也可以分着查
        Set<Long> cids = articleCollectMapper.selectList(
                new LambdaQueryWrapper<ArticleCollect>().eq(ArticleCollect::getArticleId, articleId))
                .stream().map(ArticleCollect::getCollectionId).collect(Collectors.toSet());
        // 获取指定ID的User的收藏集
        return collectionMapper.selectList(
                new LambdaQueryWrapper<Collection>()
                        .eq(Collection::getUserId, userId)
                        .in(Collection::getId, cids));

    }

    @Override
    public Collection getCollectionById(Long cid) {
        ValidateUtils.validateId(cid);
        return this.getById(cid);
    }

    @Override
    public boolean deleteCollection(Long cid) {

        // 1.获取该收藏夹下的文章，将收藏数-1
        List<ArticleCollect> acs = articleCollectMapper.selectList(new LambdaQueryWrapper<ArticleCollect>().eq(ArticleCollect::getCollectionId, cid));
        Set<Long> articlesId = acs.stream().map(ArticleCollect::getArticleId).collect(Collectors.toSet());
        boolean b1 = articleMapper.decrMulCollectCount(articlesId);

        // 2.删除文章-收藏表的联系
        int b2 = articleCollectMapper.delete(new LambdaQueryWrapper<ArticleCollect>().eq(ArticleCollect::getCollectionId, cid));

        // 3.删除收藏夹本身
        int b3 = collectionMapper.deleteById(cid);
        return b1 && b2 > 0 && b3 > 0;
    }

    @Override
    public boolean updateOne(Collection collection) {
        return this.updateById(collection);
    }

}


