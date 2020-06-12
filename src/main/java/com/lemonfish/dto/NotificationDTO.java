package com.lemonfish.dto;

import com.lemonfish.entity.Notification;
import com.lemonfish.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/11 7:30
 */
@Data
@Accessors(chain = true)
public class NotificationDTO  {
    /**
     * 通知的ID，用于修改状态
     */
    private Long id;
    /**
     * 通知的发出者
     */
    private User notifier;
    /**
     * 通知动作（点赞、评论、回复、收藏）
     */
    private Integer notifyAction;
    /**
     * 通知的具体内容
     */
    private String notifyContent;
    /**
     * 目标ID,用于页面跳转
     */
    private Long targetId;
    /**
     * 目标的标题（比如回复了你的评论XXX，评论了你的文章XXX）
     */
    private String targetTitle;
    /**
     * 目标的类型
     */
    private Integer targetType;
    /**
     * 通知状态
     */
    private Integer nStatus;
    /**
     * 通知的发出时间
     */
    private Date createdTime;

}
