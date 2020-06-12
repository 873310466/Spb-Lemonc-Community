package com.lemonfish.dto;

import com.lemonfish.entity.Comment;
import com.lemonfish.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/9 3:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentDTO extends Comment {
    private User user;

    private User targetUser;

}
