package com.lemonfish.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.entity.Notification;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.NotificationService;
import com.lemonfish.util.MyJsonResult;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
  *
  * @author LemonFish
  * @since 2020-05-11
  */
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取用户的全部通知
     * @param userId
     * @return
     */
    @GetMapping("/{id}")
    public MyJsonResult getNotification(@PathVariable("id") Long userId) {
        // 失败逻辑在service里面，即使List size为0也要传递过去，怎么处理是前端的事情
        return MyJsonResult.success(notificationService.getNotification(userId));
    }

    /**
     * 获取用户未读通知的数量
     */
    @GetMapping("/unread")
    public MyJsonResult getUnreadNotificationCount(@RequestParam("id") Long id) {
        Integer count = notificationService.getUnreadNotificationCount(id);
        return MyJsonResult.success(count);
    }


    /**
     * 读取ID为XX的通知
     * @param nid
     * @return
     */
    @PutMapping("/{id}")
    public MyJsonResult readNotification(@PathVariable("id") Long nid) {
        return notificationService.readNotification(nid)?MyJsonResult.success():MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 删除单个
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public MyJsonResult deleteNotification(@PathVariable("id") Long id) {
        ValidateUtils.validateId(id);
        boolean flag = notificationService.removeById(id);
        return flag?MyJsonResult.success(): MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 删除全部
     * @param uid
     * @return
     */
    @DeleteMapping("/all/{uid}")
    public MyJsonResult deleteAllNotification(@PathVariable("uid") Long uid) {
        ValidateUtils.validateId(uid);
        boolean flag = notificationService.remove(new LambdaQueryWrapper<Notification>().eq(Notification::getReceiverId, uid));
        return flag?MyJsonResult.success(): MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 已读全部
     * @param uid
     * @return
     */
    @PutMapping("/all/{uid}")
    public MyJsonResult readAllNotification(@PathVariable("uid")Long uid) {
        boolean result = notificationService.readAllNotification(uid);
        return result?MyJsonResult.success(): MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }
}
