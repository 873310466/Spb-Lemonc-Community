package com.lemonfish.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lemonfish.dto.ArticleSearchTempDTO;
import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.dto.ArticleRelatedDTO;
import com.lemonfish.entity.Article;
import com.lemonfish.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *   * 文章表 Mapper 接口
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-07
 *  
 */
@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    boolean incrViewCount(Long id);

    boolean incrViewComment(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 获取一篇文章的相关文章
     *
     * @param wrapper
     * @return
     */
    @Select("select a.id,a.title,a.like_count,a.comment_count from article a left join article_tag a_t on a.id=a_t.article_id ${ew.customSqlSegment}")
    List<ArticleRelatedDTO> getRelatedArticles(@Param(Constants.WRAPPER) Wrapper<ArticleTag> wrapper);

    /**
     * 点赞文章
     * @param articleId
     * @return
     */
    @Update("update article set like_count = like_count + 1 where id = #{articleId}")
    boolean incrLikeCount(Long articleId);

    /**
     * 取消点赞
     * @param articleId
     * @return
     */
    @Update("update article set like_count = like_count - 1 where id = #{articleId}")
    boolean decrLikeCount(Long articleId);

    /**
     * 取消收藏一篇文章
     *
     * @param articleId
     * @return
     */
    @Update("update article set collect_count = collect_count - 1 where id = #{articleId}")
    boolean decrOneCollectCount(Long articleId);

    /**
     * 取消收藏多篇文章
     * @param articleId
     * @return
     */
    boolean decrMulCollectCount(@Param("aids") Set<Long> articleId);

    /**
     * 收藏文章
     * @param articleId
     * @return
     */
    @Update("update article set collect_count = collect_count + 1 where id = #{articleId}")
    boolean incrCollectComment(Long articleId);

    List<Article> getHottestArticles(@Param("id") Long id,
                                     @Param("curPage") Long curPage,
                                     @Param("size") Long size);

    List<ArticleSearchTempDTO> getSearchTempInfo(@Param("set") Set<Long> ids);


    @Cacheable("getArticleDetail")
    @Select("select detail from article where id = #{id}")
    String getArticleDetail(Long articleId);

    @Select("select id from article")
    Set<Long> selectAllId();

    void updateAllViewCount(@Param("map") Map<Long, Integer> map);

    @Select("select version from article where id = #{id}")
    int getVersion(Long id);
}
