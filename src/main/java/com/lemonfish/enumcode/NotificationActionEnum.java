package com.lemonfish.enumcode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/11 22:47
 */
public enum NotificationActionEnum {
    /**
     * 点赞
     */
    THUMB_UP(1,"点赞"),
    REPLY_ARTICLE(2,"回复文章"),
    REPLY_COMMENT(3,"回复问题"),
    COLLECT(4, "收藏"),
    ;
    private Integer action;
    private String description;

    public Integer getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    NotificationActionEnum(Integer action, String description) {
        this.action = action;
        this.description = description;
    }
}
