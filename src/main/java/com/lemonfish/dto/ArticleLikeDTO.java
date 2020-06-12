package com.lemonfish.dto;

import com.lemonfish.entity.ArticleLike;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/11 22:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleLikeDTO extends ArticleLike {
    private Long receiverId;

    private String articleTitle;

}
