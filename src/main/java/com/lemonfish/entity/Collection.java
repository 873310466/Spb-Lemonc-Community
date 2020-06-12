package com.lemonfish.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.Version;
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
 * 收藏集表
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Collection对象", description="收藏集表")
public class Collection extends BaseEntity {

    private static final long serialVersionUID=1L;

    private String name;

    private String detail;

    private Integer count;

    private Long userId;

/*    *//**
     * 乐观锁字段
     *//*
    @Version
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer version=0;*/
}
