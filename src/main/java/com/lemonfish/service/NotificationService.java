package com.lemonfish.service;

import com.lemonfish.dto.NotificationDTO;
import com.lemonfish.entity.Notification;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  *  服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-11
  */
public interface NotificationService extends IService<Notification> {
    List<NotificationDTO> getNotification(Long userId);

    boolean readNotification(Long nid);

    boolean readAllNotification(Long uid);

    /**
     * 获取用户未读通知的数量
     * @param id
     * @return
     */
    Integer getUnreadNotificationCount(Long id);
}
