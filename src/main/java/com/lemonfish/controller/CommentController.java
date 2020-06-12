package com.lemonfish.controller;

import com.lemonfish.entity.Comment;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.CommentService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
  *
  * @author LemonFish
  * @since 2020-05-08
  */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发表评论
     * @param comment
     * @return
     */
    @PostMapping("/")
    public MyJsonResult saveComment(@RequestBody Comment comment) {
        boolean result = commentService.saveComment(comment);
        return result ? MyJsonResult.success(comment.getId()) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

}
