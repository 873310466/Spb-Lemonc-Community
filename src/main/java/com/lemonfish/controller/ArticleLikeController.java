package com.lemonfish.controller;
import com.lemonfish.dto.ArticleLikeDTO;
import com.lemonfish.entity.ArticleLike;
import com.lemonfish.service.ArticleLikeService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    /**
     * 点赞文章
     * @param articleLike
     * @return
     */
    @PostMapping("/")
    public MyJsonResult thumbUpArticle(@RequestBody ArticleLikeDTO articleLike) {
        return MyJsonResult.success(articleLikeService.thumbUpArticle(articleLike));
    }

    @DeleteMapping("/")
    public MyJsonResult cancelThumbUpArticle(@RequestBody ArticleLike articleLike) {
        return MyJsonResult.success(articleLikeService.cancelThumbUpArticle(articleLike));
    }

    @GetMapping("/is")
    public MyJsonResult isThumbUp(@RequestParam("aid")Long aid,@RequestParam("uid")Long uid) {
        boolean thumbUp = articleLikeService.isThumbUp(aid, uid);
        return MyJsonResult.success(thumbUp);
    }


}
