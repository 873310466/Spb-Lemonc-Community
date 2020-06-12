package com.lemonfish.entity;

import com.lemonfish.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ArticleCollect对象", description="文章收藏表")
public class ArticleCollect extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Long articleId;

    /**
     * 字段冗余的用处
     */
    private Long userId;

    private Long collectionId;


}
