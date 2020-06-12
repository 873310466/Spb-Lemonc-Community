package com.lemonfish.enumcode;

/**
 * 社区榜单信息
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.enumcode
 * @date 2020/5/20 12:39
 */
public enum InfoEnum {
    /**
     * 社区前缀，get时用
     */
    LEMONC_PREFIX("LEMONC:", "社区前缀"),
    /**
     * 用户ID前缀
     */
    USER_ID_PREFIX("userId:", "用户ID前缀"),
    /**
     * 用户信息前缀
     */
    LEMONC_USERINFO_PREFIX("LEMONC:userId:", "社区用户信息前缀"),


    /**
     * 文章浏览量计数前缀
     */
    VIEW_ARTICLE("LEMONC:viewCount:", "文章浏览量计数前缀+ID即可"),

    /**
     * 用户是否浏览过该文章前缀
     */
    IS_VIEWED_PREFIX("LEMONC:isViewed:articleId:", "用户是否浏览过该文章前缀"),

    /**
     * 用户文章浏览量榜单
     */
    VIEW_LIST("LEMONC:UserViewList", "用户文章浏览量榜单"),
    /**
     * 用户文章点赞量榜单
     */
    LIKE_LIST("LEMONC:UserLikeList", "用户文章点赞量榜单"),
    /**
     * 用户文章收藏量榜单
     */
    COLLECTION_LIST("LEMONC:UserCollectionList", "用户文章收藏量榜单");


    private String name;
    private String description;

    InfoEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
