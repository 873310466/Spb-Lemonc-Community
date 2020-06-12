package com.lemonfish.controller;
import com.lemonfish.dto.CommentLikeDTO;
import com.lemonfish.entity.ArticleLike;
import com.lemonfish.entity.Comment;
import com.lemonfish.entity.CommentLike;
import com.lemonfish.service.CommentLikeService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    /**
     * 点赞文章
     * @param commentLike
     * @return
     */
    @PostMapping("/")
    public MyJsonResult thumbUpArticle(@RequestBody CommentLikeDTO commentLike) {
        return MyJsonResult.success(commentLikeService.thumbUpComment(commentLike));
    }

    @DeleteMapping("/")
    public MyJsonResult cancelThumbUpArticle(@RequestBody CommentLike commentLike) {
        return MyJsonResult.success(commentLikeService.cancelThumbUpComment(commentLike));
    }

    @GetMapping("/is")
    public MyJsonResult isThumbUp(@RequestParam("cid")Long cid,@RequestParam("uid")Long uid) {
        boolean thumbUp = commentLikeService.isThumbUp(cid, uid);
        return MyJsonResult.success(thumbUp);
    }

    @GetMapping("/{aid}/{uid}")
    public MyJsonResult getCommentIDSet(@PathVariable("aid") Long aid, @PathVariable("uid") Long uid) {
        return MyJsonResult.success(commentLikeService.getCommentIDSet(aid, uid));
    }

}
