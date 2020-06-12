package com.lemonfish.controller.publicController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemonfish.dto.ArticleRelatedDTO;
import com.lemonfish.entity.Article;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.ArticleService;
import com.lemonfish.util.MyJsonResult;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.controller
 * @date 2020/5/8 11:49
 */
@RestController
@RequestMapping("/public/article")
public class PublicArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 按时间排序
     * 个人·获取分页文章
     * 当不携带id时，就是获取全部
     */
    @GetMapping("/list")
    public MyJsonResult getNewestArticles(
            @RequestParam(value = "curPage", defaultValue = "1") Long curPage,
            @RequestParam(value = "size", defaultValue = "7") Long size,
            @RequestParam(value = "id",required = false) Long id) {

        IPage<Article> articles = articleService.getNewestArticles(id, curPage, size);

        return MyJsonResult.success(articles);
    }

    /**
     * 获取热度文章
     */
    @GetMapping("/list/hottest")
    public MyJsonResult getHottestArticles(
            @RequestParam(value = "curPage", defaultValue = "1") Long curPage,
            @RequestParam(value = "size", defaultValue = "7") Long size,
            @RequestParam(value = "id",required = false) Long id) {

        List<Article> articles = articleService.getHottestArticles(id, curPage, size);

        // 总数，用于搜索的分页
        int count= articleService.count();

        return MyJsonResult.success(articles,count);
    }

    /**
     * 搜索文章
     */
    @GetMapping("/search")
    public MyJsonResult searchArticles(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @RequestParam(value = "size", defaultValue = "7") int size,
            @RequestParam(value = "type",defaultValue = "-1") int type,
            @RequestParam(value = "keyword") String keyword) {

        List<Article> articles = articleService.searchMulWithHighLight(keyword,type, curPage, size);

        return MyJsonResult.success(articles);
    }

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 增加文章的浏览量
     * @param request
     * @return
     */
    @GetMapping("/view_count")
    public MyJsonResult incrViewCount(@RequestParam("aid")Long aid,
                                      @RequestParam("uid")Long uid,
                                      HttpServletRequest request) {
        boolean result = articleService.incrViewCount(aid,uid,request);
        return result ? MyJsonResult.success() : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }


    /**
     * 根据文章ID获取文章数据
     */
    @GetMapping("/{id}")
    public MyJsonResult getArticleById(@PathVariable("id") Long id) {

        Article article = articleService.getDetail(id);
        return article != null ? MyJsonResult.success(article) : MyJsonResult.fail(CodeEnum.FAIL_NOT_FOUND);
    }

    /**
     * 根据文章ID获取 相关文章数据
     */
    @GetMapping("/related/{id}")
    public MyJsonResult getRelatedArticleById(@PathVariable("id") Long id) {
        List<ArticleRelatedDTO> articleRelated = articleService.getRelatedArticle(id);
        return articleRelated != null ? MyJsonResult.success(articleRelated) : MyJsonResult.fail(CodeEnum.FAIL_NOT_FOUND);
    }

}
