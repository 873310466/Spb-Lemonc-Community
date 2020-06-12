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
 * 评论点赞表
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CommentLike对象", description="评论点赞表")
public class CommentLike extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Long commentId;

    private Long userId;

    private Long articleId;

}
