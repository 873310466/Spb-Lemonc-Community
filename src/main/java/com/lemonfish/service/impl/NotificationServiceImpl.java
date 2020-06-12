package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.NotificationDTO;
import com.lemonfish.entity.BaseEntity;
import com.lemonfish.entity.Notification;
import com.lemonfish.entity.User;
import com.lemonfish.mapper.NotificationMapper;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
  * <p>
  *  服务实现类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@Service
@Transactional
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {


    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<NotificationDTO> getNotification(Long userId) {
        // 先判断id是否越界
        ValidateUtils.validateId(userId);
        // 构造查询条件
        LambdaQueryWrapper<Notification> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Notification::getReceiverId, userId).orderByAsc(Notification::getNStatus).orderByDesc(BaseEntity::getCreatedTime);
        // 拿到该用户的所有通知信息
        List<Notification> notifications = notificationMapper.selectList(lqw);
        // 筛选出每个通知者的ID并且去重，因为每个通知都需要有个发出者，我们利用ID去拿到对应的User信息
        if (notifications != null && notifications.size() > 0) {
            Set<Long> notifiersId = notifications.stream().map(Notification::getNotifierId).collect(Collectors.toSet());
            List<User> notifiers = userMapper.selectBatchIds(notifiersId);
            // 这里讲一下为什么需要建立一个Map，
            // 因为我们后面需要为每个通知匹配对应的发出者，
            // 如果使用双重for循环匹配，时间复杂度为n方，不可取
            // 因此采用Map这种方式进行优化
            Map<Long, User> notifiersMap = notifiers.stream().collect(Collectors.toMap(BaseEntity::getId, user -> user));

            // 构建DTO
            List<NotificationDTO> notificationDTOS = notifications.stream().map(item -> {
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(item, notificationDTO);
                notificationDTO.setNotifier(notifiersMap.get(item.getNotifierId()));
                return notificationDTO;
            }).collect(Collectors.toList());
            return notificationDTOS;
        }


        return new ArrayList<>();
    }

    @Override
    public boolean readNotification(Long nid) {
        return notificationMapper.readNotification(nid);
    }

    @Override
    public boolean readAllNotification(Long uid) {
        return notificationMapper.readAllNotification(uid);
    }

    @Override
    public Integer getUnreadNotificationCount(Long id) {
        ValidateUtils.validateId(id);

        return notificationMapper.selectCount(
                new LambdaQueryWrapper<Notification>()
                .eq(Notification::getReceiverId, id)
                .eq(Notification::getNStatus, 0));
    }

}


