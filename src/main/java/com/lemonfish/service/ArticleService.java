package com.lemonfish.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemonfish.dto.ArticleDTO;
import com.lemonfish.dto.ArticleRelatedDTO;
import com.lemonfish.entity.Article;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  * <p>
 *  * 文章表 服务类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-07
 *  
 */
public interface ArticleService extends IService<Article> {

    /**
     * ES 高亮分页
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     */
    List<Article>searchMulWithHighLight(String keyword, int type, int curPage, int pageSize);

    /**
     * 获取用户收藏夹里面的文章
     *
     * @param curPage
     * @param size
     * @param cid
     * @return
     */
    IPage<Article> getArticleInCollection(Long curPage, Long size, Long cid);


    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    boolean saveOrupdateArticle(ArticleDTO article);

    IPage<Article> getNewestArticles(Long curPage, Long size);

    IPage<Article> getNewestArticles(Long id, Long curPage, Long size);


    /**
     * 获取文章具体数据
     *
     * @param id
     * @return
     */
    Article getDetail(Long id);

    /**
     * 增加浏览数
     *
     * @param aid
     * @param uid
     * @param request
     * @return
     */
    boolean incrViewCount(Long aid, Long uid, HttpServletRequest request);

    /**
     * 增加点赞数
     */
    boolean incrLikeCount(Long id);

    /**
     * 根据文章ID获取相关文章的数据
     *
     * @param id
     * @return
     */
    List<ArticleRelatedDTO> getRelatedArticle(Long id);


    /**
     * 校验是否有修改文章的权限
     * @param aid
     * @param uid
     * @return
     */
    boolean validatePermission(Long aid, Long uid);

    /**
     * 获取热度优先的文章
     * @param id
     * @param curPage
     * @param size
     * @return
     */
    List<Article> getHottestArticles(Long id, Long curPage, Long size);



}
