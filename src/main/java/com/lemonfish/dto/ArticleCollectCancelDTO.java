package com.lemonfish.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 用来取消 1 个收藏夹下的 n 篇文章
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/18 16:35
 */
@Data
@Accessors(chain = true)
public class ArticleCollectCancelDTO {
    /**
     * 收藏夹ID
     */
    private Long collectionId;

    /**
     * 文章ID集合
     */
    private Set<Long> articlesId;
}
