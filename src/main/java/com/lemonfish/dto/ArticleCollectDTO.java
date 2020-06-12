package com.lemonfish.dto;

import com.lemonfish.entity.ArticleCollect;
import com.lemonfish.entity.ArticleLike;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/11 22:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCollectDTO extends ArticleCollect {

    /**
     * 用来通知作者被收藏了
     */
    private Long authorId;

    /**
     * 文章标题，用来跳转
     */
    private String articleTitle;
    /**
     * 收藏夹集合
     */
    private Set<Long> collectionsId;

}
