package com.lemonfish.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

import java.util.List;

/**
 * <p>
 * 标签表
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
@ApiModel(value="Tag对象", description="标签表")
public class Tag extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "标签名字")
    private String name;

    @ApiModelProperty(value = "父级标签ID")
    private Long parentId;

    @TableField(exist = false)
    @ApiModelProperty(value = "字标签ID集合")
    private List<Tag> children = null;
}
