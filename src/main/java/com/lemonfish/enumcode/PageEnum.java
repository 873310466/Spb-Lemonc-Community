package com.lemonfish.enumcode;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/11 7:44
 */
public enum PageEnum {
    MIN_SIZE(5L),
    MID_SIZE(7L),
    MAX_SIZE(10L);

    private Long curPage;
    private final Long size;

    PageEnum(Long curPage, Long size) {
        this.curPage = curPage;
        this.size = size;
    }

    PageEnum(Long size) {
        this.size = size;
    }

    public Long getCurPage() {
        return curPage;
    }

    public Long getSize() {
        return size;
    }
}
