package com.lemonfish.mapper;

import com.lemonfish.entity.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 *   *  Mapper 接口
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-11
 *  
 */
@Repository
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {

    /**
     * 读取一个信息
     * @param nid
     * @return
     */
    @Update("update notification set n_status = 1 where n_status = 0 and id = #{nid}")
    boolean readNotification(Long nid);

    /**
     * 读取全部信息
     * @param uid
     * @return
     */
    @Update("update lemonc.notification set n_status = 1 where n_status = 0 and is_deleted = 0 and receiver_id = #{uid}")
    boolean readAllNotification(Long uid);
}
