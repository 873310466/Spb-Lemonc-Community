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
 * @since 2020-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="OauthMap对象", description="")
public class OauthMap extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "第三方用户的ID")
    private Long oauthId;

    @ApiModelProperty(value = "第三方是什么")
    private String source;

    @ApiModelProperty(value = "映射的用户ID")
    private Long userId;


}
