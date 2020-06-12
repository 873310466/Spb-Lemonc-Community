package com.lemonfish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.dto.ArticleDTO;
import com.lemonfish.dto.ArticleRelatedDTO;
import com.lemonfish.dto.ArticleSearchTempDTO;
import com.lemonfish.entity.*;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.exception.BaseException;
import com.lemonfish.mapper.ArticleCollectMapper;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.ArticleTagMapper;
import com.lemonfish.mapper.TagMapper;
import com.lemonfish.repository.ArticleRepository;
import com.lemonfish.service.ArticleService;
import com.lemonfish.util.IPUtil;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import com.lemonfish.util.ValidateUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// import com.lemonfish.config.elastic_search.HighlightResultMapper;
// import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 *  * <p>
 *  * 文章表 服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-07
 *  
 */
@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleCollectMapper articleCollectMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    ElasticsearchRestTemplate ESRestTemplate;

    @Override
    public List<Article> searchMulWithHighLight(String keyword, int type, int curPage, int pageSize) {

        // 高亮设置
        String preTags = "<span style=\"color:#F56C6C\">";
        String postTags = "</span>";


        // 时间范围
        String from;
        String to = "now";
        switch (type) {
            case 1:
                from = "now-1d/d";
                break;
            case 7:
                from = "now-7d/d";
                break;
            case 90:
                from = "now-90d/d";
                break;
            default:
                from = "2020-01-01";
                break;
        }

        // 查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.multiMatchQuery(keyword, "title", "detail")) // 关键词查找
                        .must(QueryBuilders.rangeQuery("createdTime").from(from).to(to))) // 时间范围查找
                .withHighlightBuilder(new HighlightBuilder().field("title").field("detail").preTags(preTags).postTags(postTags)) // 高亮
                .withPageable(PageRequest.of(curPage - 1, pageSize))         // 设置分页参数
                .build();


        // 执行搜索，获取结果
        SearchHits<Article> contents = ESRestTemplate.search(searchQuery, Article.class);
        List<SearchHit<Article>> articles = contents.getSearchHits();
        // 如果list的长度为0，直接return
        if (articles.size() == 0) {
            return new ArrayList<>();
        }

        // 获取文章ID，以便获取用户信息。
        Set<Long> articlesId = articles.stream().map(item->item.getContent().getId()).collect(Collectors.toSet());
           // 获取文章的一些展示的辅助信息，比如点赞量、评论数等等
        // 形成Map，方便下面进行一一匹配
        Map<Long, ArticleSearchTempDTO> map = articleMapper.getSearchTempInfo(articlesId).stream().collect(Collectors.toMap(ArticleSearchTempDTO::getId, item -> item));

        // 完成真正的映射，拿到展示的文章数据。
        List<Article> result = articles.stream().map(article -> {
            // 获取高亮数据
            Map<String, List<String>> highlightFields = article.getHighlightFields();

            //如果集合不为空说明包含高亮字段，则添加。
            if (!CollectionUtils.isEmpty(highlightFields.get("title"))) {
                article.getContent().setTitle(highlightFields.get("title").get(0));
                System.out.println("titleSize:"+highlightFields.get("title").size());
            }
            System.out.println("----------****************--------------");
            if (!CollectionUtils.isEmpty(highlightFields.get("detail"))) {
                article.getContent().setDetail(highlightFields.get("detail").get(0));
                System.out.println("detailSize:"+highlightFields.get("detail").size());
            }

            // 真正展示的是ArticleDTO,DTO包含标签信息以及用户信息
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article.getContent(), articleDTO);

            // 文章展示的辅助信息，比如点赞量、评论数等等
            ArticleSearchTempDTO tempInfo = map.get(article.getContent().getId());
            // 从缓存中拉取浏览量 加上 数据库的浏览量 = 真正的浏览量
            int cache = redisUtil.pfCount(InfoEnum.VIEW_ARTICLE.getName() + article.getId());
            tempInfo.setViewCount(tempInfo.getViewCount() + cache);
            BeanUtils.copyProperties(tempInfo, articleDTO);

            // 设置文章作者信息
            articleDTO.setUser(userFactory.build(article.getContent().getAuthorId()).setEmail(""));
            List<Tag> tagList = tagMapper.getTagsById(articleDTO.getId()).stream().filter(item -> item.getId() > 100_100_0).collect(Collectors.toList());
            // 设置 Tag
            articleDTO.setTagList(tagList);
            return articleDTO;
        }).collect(Collectors.toList());



        return result;
    }

/*    @Override
    public List<Article> searchMulWithHighLight(String keyword, int type, int curPage, int pageSize) {
        return null;
    }*/

    /**
     * 获取用户收藏夹下的文章
     *
     * @param curPage
     * @param size
     * @param cid
     * @return
     */
    @Override
    public IPage<Article> getArticleInCollection(Long curPage, Long size, Long cid) {
        ValidateUtils.validateId(cid);

        // 条件构造，获取cid的收藏夹的文章，并按时间倒序
        LambdaQueryWrapper<ArticleCollect> lqw = new LambdaQueryWrapper<>();
        lqw
                .orderByDesc(BaseEntity::getCreatedTime)
                .eq(ArticleCollect::getCollectionId, cid);
        List<ArticleCollect> articleCollects = articleCollectMapper.selectList(lqw);
        if (articleCollects.size() == 0) {
            return null;
        }
        // 获取对应收藏夹下的文章编号
        Set<Long> articlesId = articleCollects.stream().map(ArticleCollect::getArticleId).collect(Collectors.toSet());
        // 根据文章编号进行分页查找
        Page<Article> articlePage = articleMapper.selectPage(new Page<>(curPage, size), new LambdaQueryWrapper<Article>().in(Article::getId, articlesId));
        // 匹配文章的作者
        List<Article> articleList = matchUserToArticle(articlePage.getRecords());
        articlePage.setRecords(articleList);
        return articlePage;


    }


    /**
     * 保存或者更新
     *
     * @param article
     * @return
     */
    @Override
    public boolean saveOrupdateArticle(ArticleDTO article) {
        boolean result = this.saveOrUpdate(article);
        if (result) {
            // 如果添加成功，则把文章和标签进行联系
            List<Long> tagList = article.getTagIdList();
            // 利用stream构建文章和标签的联系
            List<ArticleTag> articleTags = tagList.stream().map(item -> new ArticleTag(article.getId(), item)).collect(Collectors.toList());
            // 批量添加tag联系
            articleTagMapper.saveOrUpdateBatch(articleTags);
            // 重置缓存
            redisUtil.del("getArticleDetail::" + article.getId());
            redisUtil.del("getTagsById::" + article.getId());
        }
        return result;
    }

    @Override
    public IPage<Article> getNewestArticles(Long curPage, Long size) {
        return this.getNewestArticles(null, curPage, size);
    }

    /**
     * 根据时间进行排序文章
     *
     * @param id
     * @param curPage
     * @param size
     * @return
     */
    @Override
    public IPage<Article> getNewestArticles(Long id, Long curPage, Long size) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        // 仅用于页面展示不应该把文章全文列出来
        lqw.select(Article.class, info -> !"detail".equals(info.getColumn())).orderByDesc(BaseEntity::getCreatedTime);
        if (id != null) {
            lqw.eq(Article::getAuthorId, id);
        }
        Page<Article> articlePage = articleMapper.selectPage(new Page<>(curPage, size), lqw);
        // 如果size=0，直接返回即可。
        if (articlePage.getRecords().size() == 0) {
            return articlePage;
        }
        List<Article> articleList = matchUserToArticle(articlePage.getRecords());
        articlePage.setRecords(articleList);
        return articlePage;
    }


    /**
     * 用户-文章 匹配
     *
     * @param articles
     * @return
     */
    private List<Article> matchUserToArticle(List<Article> articles) {
        return articles.stream().map(this::getArticleDTO).collect(Collectors.toList());
    }


    /**
     * 获取热度优先的文章
     *
     * @param id
     * @param curPage
     * @param size
     * @return
     */
    @Override
    public List<Article> getHottestArticles(Long id, Long curPage, Long size) {
        // 如果标题为空，说明是非搜索
        List<Article> hottestArticles = articleMapper.getHottestArticles(id, (curPage - 1) * size, size);
        // 匹配作者和标签
        return this.matchUserToArticle(hottestArticles);
    }




    /**
     * 根据文章ID获取文章
     *
     * @param id
     * @return
     */
    @Override
    public Article getDetail(Long id) {
        Article article = articleMapper.selectOne(
                // 根据主键选
                // 不选detail
                new LambdaQueryWrapper<Article>()
                        .select(Article.class, info -> !"detail".equals(info.getColumn()))
                        .eq(Article::getId, id));
        article.setDetail(articleMapper.getArticleDetail(id));
        return this.getArticleDTO(article);
    }


    /**
     * 增加浏览数
     *
     * @param aid
     * @param uid
     * @param request
     * @return
     */
    @Override
    public boolean incrViewCount(Long aid, Long uid, HttpServletRequest request) {

        // 文章浏览量前缀+文章ID
        String key = InfoEnum.VIEW_ARTICLE.getName() + aid;
        // IP地址
        String value = IPUtil.getIpFromRequest(request);
        //System.out.println(value);

        int before = redisUtil.pfCount(key);
        redisUtil.pfAdd(key, value);
        int after = redisUtil.pfCount(key);
        if (after - before > 0) {
            redisUtil.zIncrScore(InfoEnum.VIEW_LIST.getName(),
                    InfoEnum.USER_ID_PREFIX.getName() + uid,
                    1);
            return true;
        } else {
            return false;
        }

       /* if (redisUtil.get(getIsView(aid, remoteAddr)) == null) {
            // 将key设置到redis里面，防止用户刷浏览量
            redisUtil.set(getIsView(aid, remoteAddr), "", 3600);
            boolean res = articleMapper.incrViewCount(aid);
            if (res) {
                // 排行榜 浏览量 + 1
                redisUtil.zIncrScore(InfoEnum.VIEW_LIST.getName(),
                        InfoEnum.USER_ID_PREFIX.getName() + uid,
                        1);
                return res;
            }
        }*/


    }

    /**
     * 获取redis中是否浏览文章的key
     *
     * @param id
     * @param remoteAddr
     * @return
     */
    private String getIsView(Long id, String remoteAddr) {
        return InfoEnum.IS_VIEWED_PREFIX.getName() + id + "IP:" + remoteAddr;
    }


    @Override
    public boolean incrLikeCount(Long id) {
        return articleMapper.incrLikeCount(id);
    }

    /**
     * 根据文章ID获取相关文章的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<ArticleRelatedDTO> getRelatedArticle(Long id) {
        // 1.根据文章ID获取对应的Tag ID，并且筛选出最后一级Tag
        Set<Long> tagsId = tagMapper.getArticlesIdByTagId(id).stream().filter(item -> item > 100_100_0).collect(Collectors.toSet());

        QueryWrapper<ArticleTag> lqw = new QueryWrapper<>();
        // 2.接下来我们要查找根据查询出来的Tag ID去找到对应的文章
        // 条件是：1.ID不等于自己文章  2。看sql吧不好描述
        lqw.in("a_t.tag_id", tagsId)
                .ne("a.id", id)
                .last("order by a.like_count desc limit 0,5");
        // 因为查出来的可能有重，所以进行去重
        return articleMapper.getRelatedArticles(lqw).stream().distinct().collect(Collectors.toList());
    }


    /**
     * 校验是否有修改文章的权限
     *
     * @param aid
     * @param uid
     * @return
     */
    @Override
    public boolean validatePermission(Long aid, Long uid) {
        // 0.校验id合理性
        ValidateUtils.validateId(aid);
        ValidateUtils.validateId(uid);
        // 1.首先建议前端传过来的ID是否是redis里面存储用户的id
        Article article = articleMapper.selectById(aid);
        boolean equals = article.getAuthorId().equals(uid);
        if (!equals) {
            throw new BaseException(CodeEnum.DANGEROUS_WARNING);
        }
        return true;
    }


    /**
     * 封装一个，根据article封装成articleDTO的方法
     *
     * @param article
     * @return
     */
    private ArticleDTO getArticleDTO(Article article) {
        //现在我们要通过article去组装articleDTO
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        // 0.从缓存中拉去浏览量 加上 数据库的浏览量
        int cache = redisUtil.pfCount(InfoEnum.VIEW_ARTICLE.getName() + article.getId());
        articleDTO.setViewCount(articleDTO.getViewCount() + cache);
        // 获取ID，去寻找User和Tag
        List<Tag> tagList = tagMapper.getTagsById(articleDTO.getId()).stream().filter(item -> item.getId() > 100_100_0).collect(Collectors.toList());
        // 1.设置 Tag
        articleDTO.setTagList(tagList);
        // 2.设置 User
        User user = userFactory.build(article.getAuthorId());
        articleDTO.setUser(user);
        return articleDTO;
    }
}


