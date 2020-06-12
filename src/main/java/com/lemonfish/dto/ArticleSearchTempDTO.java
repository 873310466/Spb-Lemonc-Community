package com.lemonfish.dto;


import lombok.Data;

/**
 * 搜索时需要的附带信息
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/6/8 10:06
 */
@Data
public class ArticleSearchTempDTO {
    private Long id;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;
}
