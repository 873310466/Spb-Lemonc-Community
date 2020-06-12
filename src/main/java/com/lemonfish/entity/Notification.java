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
 *
 * </p>
 *
 * @author LemonFish
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Notification对象", description="消息通知")
public class Notification extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "发布者")
    private Long notifierId;

    @ApiModelProperty(value = "通知动作，比如评论、点赞、收藏等等")
    private Integer notifyAction;

    @ApiModelProperty(value = "通知的具体内容")
    private String notifyContent;

    @ApiModelProperty(value = "接收者")
    private Long receiverId;

    @ApiModelProperty(value = "目标对象ID")
    private Long targetId;

    @ApiModelProperty(value = "目标对象的内容")
    private String targetTitle;

    @ApiModelProperty(value = "目标类型，可能点赞、回复、评论等等")
    private Integer targetType;

    @ApiModelProperty(value = "状态")
    private Integer nStatus;


}
