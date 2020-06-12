package com.lemonfish.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lemonfish.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Comment对象", description="评论表")
public class Comment extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "用于二级评论挂载到一级评论")
    private Long parentId;

    @ApiModelProperty(value = "挂载到文章下面")
    private Long articleId;

    @ApiModelProperty(value = "如果是回复评论，那么你要回复了哪个评论")
    private Long targetId;

    @ApiModelProperty(value = "评论文章 Or 回复评论")
    private Integer type;

    @ApiModelProperty(value = "评论人的ID")
    private Long commentatorId;

    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;

    @TableField(exist = false)
    private List<Comment> children ;

}
