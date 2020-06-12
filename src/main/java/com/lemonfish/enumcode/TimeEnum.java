package com.lemonfish.enumcode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/29 13:57
 */
public enum TimeEnum {
    /**
     * 半个月的时间
     */
    HALF_MONTH_TIME(3600*24*15L,"半个月的时间(单位：秒)");


    private Long time;
    private String description;

    TimeEnum(Long time, String description) {
        this.time = time;
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
