package com.lemonfish.enumcode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/6/8 9:16
 */
public enum SearchTypeEnum {
    /**
     * 搜索全部
     */
    ALL(-1, "搜索全部"),

    /**
     * 搜索一天内
     */
    ONE_DAY(1, "搜索一天内"),

    /**
     * 搜索一周内
     */
    ONE_WEEK(7, "搜索一周内"),

    /**
     * 搜索三月内
     */
    THREE_MONTHS(90, "搜索三月内"),
    ;

    private int time;
    private Long millSeconds;
    private String description;

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    SearchTypeEnum(Integer time, String description) {
        this.time = time;
        this.description = description;
    }
}
