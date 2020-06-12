package com.lemonfish.enumcode;


/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/10 19:37
 */
public enum NotificationStatusEnum {
    /**
     * 未读
     */
    UNREAD(0, "未读"),
    /**
     * 已读
     */
    READ(1, "已经读");


    private Integer status;
    private String description;

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    NotificationStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
}
