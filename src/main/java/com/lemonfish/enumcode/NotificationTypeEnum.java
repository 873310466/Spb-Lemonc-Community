package com.lemonfish.enumcode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/10 19:36
 */
public enum NotificationTypeEnum {
    /**
     * 通知文章
     */
    NOTIFY_ARTICLE(1, "文章"),
    /**
     * 通知评论
     */
    NOTIFY_COMMENT(2, "评论");

    private final Integer type;
    private final String description;

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    NotificationTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}
