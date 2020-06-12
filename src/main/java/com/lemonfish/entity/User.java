package com.lemonfish.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lemonfish.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户")
public class User extends BaseEntity{

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "简介")
    private String bio;

    @ApiModelProperty(value = "头像链接")
    private String avatarUrl;

    @ApiModelProperty(value = "邮箱")
    private String email;


    /**
     * 修改时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(hidden = true)
    public Date updatedTime;

/*    *//**
     * 乐观锁字段
     *//*
    @Version
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer version=0;*/
}
