package com.lemonfish.entity;

import com.lemonfish.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章点赞表
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ArticleLike对象", description="文章点赞表")
public class ArticleLike extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;


}
