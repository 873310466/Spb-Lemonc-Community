package com.lemonfish.dto;

import com.lemonfish.entity.CommentLike;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/12 9:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentLikeDTO extends CommentLike {
    private Long receiverId;

    private String commentContent;
}
