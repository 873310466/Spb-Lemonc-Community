package com.lemonfish.controller;

import com.lemonfish.dto.ArticleCollectCancelDTO;
import com.lemonfish.dto.ArticleCollectDTO;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.ArticleCollectService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-12
 *  
 */
@RestController
@RequestMapping("/article_collect")
public class ArticleCollectController {

    @Autowired
    private ArticleCollectService articleCollectService;


    /**
     * 收藏文章
     * @param articleCollect
     * @return
     */
    @PostMapping("/")
    public MyJsonResult collectArticle(@RequestBody ArticleCollectDTO articleCollect) {
        return MyJsonResult.success(articleCollectService.collectArticle(articleCollect));
    }

    /**
     * 减少文章收藏数，只有当用户所有收藏夹都没有该文章时调用
     * @param  aid
     * @return
     */
    @DeleteMapping("/")
    public MyJsonResult cancelCollectArticle(@RequestParam("aid")Long aid,
                                             @RequestParam("uid")Long uid) {
        return MyJsonResult.success(articleCollectService.cancelCollectArticle(aid, uid));
    }

    @GetMapping("/is")
    public MyJsonResult isCollected(@RequestParam("aid") Long aid, @RequestParam("uid") Long uid) {
        return MyJsonResult.success(articleCollectService.isCollected(aid, uid));
    }

    /**
     * 取消 1 文章 -> n 收藏夹
     * @param articleCollect
     * @return
     */
    @DeleteMapping("/a/cancel")
    public MyJsonResult cancelCollectionInsideArticle(@RequestBody ArticleCollectDTO articleCollect) {
        Boolean result = articleCollectService.cancelCollectionInsideArticle(articleCollect);
        return result ? MyJsonResult.success(result) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 取消 1 收藏夹 -> n 文章
     */
    @DeleteMapping("/c/cancel")
    public MyJsonResult cancelArticleInsideCollection(@RequestBody ArticleCollectCancelDTO articleCollect) {
        Boolean result = articleCollectService.cancelArticleInsideCollection(articleCollect);
        return result ? MyJsonResult.success(result) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }
}
