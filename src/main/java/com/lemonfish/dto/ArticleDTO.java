package com.lemonfish.dto;

import com.lemonfish.entity.Article;
import com.lemonfish.entity.Tag;
import com.lemonfish.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/7 0:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDTO extends Article {
    /**
     * 文章标签ID集合
     */
    private List<Long> tagIdList;
    /**
     * 文章标签集合
     */
    private List<Tag> tagList;
    /**
     * 所属用户
     */
    private User user;

}
