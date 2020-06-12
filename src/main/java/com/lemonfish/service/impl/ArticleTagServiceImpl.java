package com.lemonfish.service.impl;


import com.lemonfish.entity.ArticleTag;
import com.lemonfish.mapper.ArticleTagMapper;
import com.lemonfish.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
/**
  * <p>
  * 文章与标签的中间表 服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-07
  */
@Service
@Transactional
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {


    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * @Description 获取所有的ArticleTag
     *
     * @return List<ArticleTag>
     */
    @Override
    public List<ArticleTag> selectArticleTagList(){
        return articleTagMapper.selectList(null);
    }

    /**
     * @Description 获取指定ID的ArticleTag
     *
     * @return ArticleTag
     */
    @Override
    public ArticleTag selectArticleTagById(Long id){
        if(id<=0){
            return null;
        }
        return articleTagMapper.selectById(id);
    }

    /**
     * @Description 更新ArticleTag
     *
     * @param articleTag
     * @return int , > 0 means success, or not
     */
    @Override
    public int updateArticleTag(ArticleTag articleTag){
        return articleTagMapper.updateById(articleTag);
    }

    /**
     * @Description 删除指定ID的ArticleTag
     *
     * @param id
     * @return int , > 0 means success, or not
     */
    @Override
    public int deleteArticleTagById(Long id){
        return articleTagMapper.deleteById(id);
    }

}


