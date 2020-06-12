package com.lemonfish;

import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.dto.UserListDTO;
import com.lemonfish.entity.Article;
import com.lemonfish.entity.User;
import com.lemonfish.mapper.*;
import com.lemonfish.repository.ArticleRepository;
import com.lemonfish.service.ArticleService;
import com.lemonfish.service.CommentService;
import com.lemonfish.service.NotificationService;
import com.lemonfish.service.UserService;
import com.lemonfish.service.impl.FileServiceImpl;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import com.xkcoding.justauth.AuthRequestFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class LemoncCommunityApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    NotificationService notificationService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    AuthRequestFactory factory;
    @Autowired
    ArticleCollectMapper articleCollectMapper;
    @Autowired
    ElasticsearchRestTemplate ESRestTemplate;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    UserFactory userFactory;
/*
    @Test
    void contextLoads() {
        ESRestTemplate.deleteIndex("article");
        ESRestTemplate.createIndex(Article.class);
        ESRestTemplate.putMapping(Article.class);
        boolean article = ESRestTemplate.indexExists("article");
        System.out.println(article);
        List<Article> articles = articleMapper.selectList(null);
        articleRepository.saveAll(articles);
        Iterable<Article> articles2 = articleRepository.findAll();
        articles2.forEach(System.out::println);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.multiMatchQuery("java", "title", "detail"));
        queryBuilder.withPageable(PageRequest.of(0, 3));

        Page<Article> items = articleRepository.search(queryBuilder.build());

        // 总条数
        System.out.println(items.getTotalElements());

        // 总页数
        System.out.println(items.getTotalPages());

        items.forEach(System.out::println);


    }


    @Test
    void getTags() throws IllegalAccessException {
*/
/*        AnalyzeRequestBuilder analyzeRequestBuilder = new AnalyzeRequestBuilder(
                elasticsearchTemplate.getClient(),
                AnalyzeAction.INSTANCE,
                "article",
                "java集合源码分析").setAnalyzer("ik_max_word");

        List<AnalyzeResponse.AnalyzeToken> ikTokenList = analyzeRequestBuilder.execute().actionGet().getTokens();

        List<String> collect = ikTokenList.stream().map(AnalyzeResponse.AnalyzeToken::getTerm).collect(Collectors.toList());

        collect.forEach(System.out::println);*//*


        System.out.println("=================");

        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion("title").prefix("jav", Fuzziness.AUTO);

        SearchResponse searchResponse = ESRestTemplate.suggest(new SuggestBuilder().addSuggestion("my-suggest", suggestionBuilder), Article.class);

        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = searchResponse.getSuggest().getSuggestion("my-suggest");

        List<? extends Suggest.Suggestion.Entry.Option> options = suggestion.getEntries().get(0).getOptions();
        System.out.println(options);

        System.out.println(options.size());

        options.forEach(item -> System.out.println(item.getText().toString()));
    }
*/

    @Test
    void testNotification() {
        int[] prices = {7, 8, 16, 4, 5};
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i]);
        }
        System.out.println(dp_i_0);

    }


    @Test
    void testNotifica() {
        userFactory.build(2L);
        userFactory.build(3L);
        // ESRestTemplate.indexOps(Article.class);
        // ESRestTemplate.indexOps(Article.class);
        // Article article = articleMapper.select ById(2L);
        // List<Article> articles = articleMapper.selectList(null);
        // articleRepository.saveAll(articles);
        // Iterable<Article> all = articleRepository.findAll();
        // all.forEach(System.out::println);
        // Article article = articleRepository.findById(5L).get();
        // articleRepository.deleteById(5L);
        // System.out.println(article);

        // Optional<Article> byId = articleRepository.findById(2L);
        // System.out.println(byId.get());
    }

    @Test
    void testNotifica1() {
        Article article = articleRepository.findById(16L).get();
        System.out.println(article.getTitle());
        System.out.println(article.getDetail());

        // List<Article> articles = articleService.searchMulWithHighLight("快捷", -1, 1, 10);
        // articles.forEach(item-> System.out.println(item.getTitle()+"------"+item.getCreatedTime()));
        // ESRestTemplate.createIndex(Article.class);
        // ESRestTemplate.putMapping(Article.class);
        // articleRepository.saveAll(articleMapper.selectList(null));
        // List<Article> test = articleService.searchMulWithHighLight("快捷键",-1, 1, 10);
        // test.forEach(item-> System.out.println(item.getViewCount()));
        // System.out.println(DateUtil.formatDateTime(new Date()));
        // MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery("快捷键", "title", "detail");
        // SearchQuery searchQuery = new NativeSearchQuery(query);
        // List<Article> articles = ESRestTemplate.queryForList(searchQuery, Article.class);
        // articles.forEach(System.out::println);
    }

    @Test
    void testNotifica2() {
        // ESRestTemplate.indexOps(Article.class).delete();
        ESRestTemplate.indexOps(Article.class);
    }




    @Test
    void testNotification1() {
        List<User> users = userMapper.selectList(null);
        Map<Long, Integer> map = new HashMap<>();
        for (User user : users) {
            //redisUtil.set("LEMONC:userId:" + user.getId(), user);
            UserAchiDTO userAchievement = userMapper.getUserAchievement(user.getId());
            if (userAchievement != null) {
                map.put(user.getId(), userAchievement.getLikeCount());
            } else {
                map.put(user.getId(), 0);
            }
        }
        redisUtil.del("LEMONC:UserLikeList");

        map.forEach((k, v) -> redisUtil.zAdd("LEMONC:UserLikeList", "userId:" + k, v));

        // 获取
        List<UserListDTO> userViewListDTOList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> testList = redisUtil.zRangeWithScoreDesc("LEMONC:UserCollectionList", 0, 2);
        testList.forEach(item -> {
            UserListDTO e = new UserListDTO();
            e.setUser((User) redisUtil.get("LEMONC:" + item.getValue()));
            e.setCount(item.getScore().intValue());
            userViewListDTOList.add(e);
            System.out.println("key:" + item.getValue() + "\tvalue:" + item.getScore());
        });


        userViewListDTOList.forEach(System.out::println);





/*        List<User> list = userService.list();
        list.forEach(item->{
            redisUtil.sSet("LEMONC:USER_INFO" ,"user:"+ item.getId(),item);
        });
        Set<Object> objects = redisUtil.sGet("LEMONC:USER_INFO");
        System.out.println(objects.size());
        assert objects != null;
        objects.forEach(item->{
            System.out.println(item);
            System.out.println("=============");
        });
        System.out.println("=======================================");*/
        //User user = (User) redisUtil.get("LEMONC:USER:" + 5705784);
        //redisUtil.keys().forEach(System.out::println);
        //redisUtil.del("LEMONC:USER_INFO", "testList");
        //userService.updateBatchById();
        //redisUtil.
        //System.out.println(user.getId());

/*        User userInfoById = userService.getUserInfoById(5705784L);
        String s = JSON.toJSONString(userInfoById);
        User user = JSON.parseObject(s, User.class);
        boolean equals = user.equals(userInfoById);
        System.out.println(equals);*/

    }

}
