package com.lemonfish.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章与标签的中间表
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ArticleTag对象", description="文章与标签的中间表")
public class ArticleTag extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章Id")
    private Long articleId;

    @ApiModelProperty(value = "标签Id")
    private Long tagId;


}
