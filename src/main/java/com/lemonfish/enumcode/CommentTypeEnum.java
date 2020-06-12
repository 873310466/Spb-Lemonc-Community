package com.lemonfish.enumcode;

import lombok.Data;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/8 20:53
 */
public enum CommentTypeEnum {
    REPLY_ARTICLE(1, "评论文章"),
    REPLY_COMMENT(2, "回复评论");

    CommentTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    private Integer type;
    private String description;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
