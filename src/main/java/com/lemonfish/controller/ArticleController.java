package com.lemonfish.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemonfish.dto.ArticleDTO;
import com.lemonfish.entity.Article;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.ArticleService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-07
 *  
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 发布/更新文章
     *
     * @param article
     * @return
     */
    @PostMapping("/")
    public MyJsonResult saveArticle(@RequestBody ArticleDTO article) {
        boolean result = articleService.saveOrupdateArticle(article);
        return result ? MyJsonResult.success(null) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 获取用户收藏夹下的文章
     *
     * @param curPage
     * @param size
     * @param cid
     * @return
     */
    @GetMapping("/collection")
    public MyJsonResult getArticleInCollection(@RequestParam(value = "curPage", defaultValue = "1") Long curPage,
                                               @RequestParam(value = "size", defaultValue = "7") Long size,
                                               @RequestParam(value = "cid") Long cid) {
        IPage<Article> articles = articleService.getArticleInCollection(curPage, size, cid);
        return MyJsonResult.success(articles);
    }


    /**
     * 校验用户是否有权限修改文章
     *
     * @return
     */
    @GetMapping("/validate")
    public MyJsonResult validatePermission(
                                           @RequestParam("aid") Long aid,
                                           @RequestParam("uid")Long uid) {
        boolean res = articleService.validatePermission(aid, uid);
        return res ? MyJsonResult.success() : MyJsonResult.fail(CodeEnum.DANGEROUS_WARNING);
    }





    /**
     * 根据文章ID获取文章数据( 编辑文章的时候 )
     */
    @GetMapping("/{id}")
    public MyJsonResult getArticleById(@PathVariable("id") Long id) {

        Article article = articleService.getDetail(id);
        return article != null ? MyJsonResult.success(article) : MyJsonResult.fail(CodeEnum.FAIL_NOT_FOUND);
    }

}
