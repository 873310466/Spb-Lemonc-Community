package com.lemonfish.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.io.Serializable;
import java.util.Date;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.entity
 * @date 2020/4/30 13:15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3627481284716806031L;
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    @Id
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    public Date createdTime;


    /**
     * 逻辑删除字段
     */
    @TableLogic
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer isDeleted=0;



}
