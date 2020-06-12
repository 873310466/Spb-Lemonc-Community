package com.lemonfish.controller.publicController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.entity.Comment;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.enumcode.CommentTypeEnum;
import com.lemonfish.service.CommentService;
import com.lemonfish.util.MyJsonResult;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.controller.publicController
 * @date 2020/5/9 2:50
 */
@RestController
@RequestMapping("/public/comment")
public class PublicCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public MyJsonResult getCommentsByArticleId(
            @PathVariable("id") Long id) {

        return MyJsonResult.success(commentService.getComments(id));
    }
}
