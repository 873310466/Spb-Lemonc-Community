package com.lemonfish.dto;

import com.lemonfish.entity.Article;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 个人成就数据类
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/8 1:06
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserAchiDTO {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 收藏数
     */
    private Integer collectCount;
    /**
     * C力值
     */
    private Double cli;


}
