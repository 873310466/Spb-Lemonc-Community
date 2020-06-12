package com.lemonfish.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.*;


import java.util.Date;

/**
 * <p>
 * 文章表
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
@ApiModel(value="Article对象", description="文章表")
@Document(indexName = "article")
public class Article extends BaseEntity {


    @ApiModelProperty(value = "文章标题")
    @Field(type = FieldType.Text, analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word")
    // @HighlightField(name="title",parameters = @HighlightParameters(preTags = "<span style=\"color:#F56C6C\">",postTags = "</span>"))
    private String title;

    @ApiModelProperty(value = "文章详情")
    // @HighlightField(name="detail",parameters = @HighlightParameters(preTags = {"<span style=\"color:#F56C6C\">"},postTags = {"</span>"}))
    @Field(type = FieldType.Text,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word")
    private String detail;

    @ApiModelProperty(value = "作者Id")
    @Field(type = FieldType.Long)
    private Long authorId;


    @ApiModelProperty(value = "浏览数")
    @Field(type = FieldType.Integer)
    private Integer viewCount=0;

    @ApiModelProperty(value = "点赞数")
    @Field(type = FieldType.Integer)
    private Integer likeCount=0;

    @ApiModelProperty(value = "评论数")
    @Field(type = FieldType.Integer)
    private Integer commentCount=0;

    @ApiModelProperty(value = "收藏数")
    @Field(type = FieldType.Integer)
    private Integer collectCount=0;

    /**
     * 修改时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @ApiModelProperty(hidden = true)
    public Date updatedTime;
/*
    *//**
     * 乐观锁字段
     *//*
    @Version
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer version=0;*/
}
