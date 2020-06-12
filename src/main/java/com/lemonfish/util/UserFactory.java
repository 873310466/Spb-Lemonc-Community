package com.lemonfish.util;

import com.lemonfish.entity.User;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.enumcode.TimeEnum;
import com.lemonfish.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/5/29 13:45
 */
@Component
public class UserFactory {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;


    public User build(Long uid) {
        User user = (User) redisUtil.get(InfoEnum.LEMONC_USERINFO_PREFIX.getName() + uid);
        // 如果为null，从redis里面获取，并设置一周时限
        if (user == null) {
            user = userMapper.selectById(uid);
            redisUtil.set(InfoEnum.LEMONC_USERINFO_PREFIX.getName() + uid, user, TimeEnum.HALF_MONTH_TIME.getTime());
        }
        return user;
    }

    public void set(User user) {
        redisUtil.set(InfoEnum.LEMONC_USERINFO_PREFIX.getName() + user.getId(), user,TimeEnum.HALF_MONTH_TIME.getTime());
    }

    public void set(Long uid,User user) {
        redisUtil.set(InfoEnum.LEMONC_USERINFO_PREFIX.getName() + uid, user,TimeEnum.HALF_MONTH_TIME.getTime());
    }
}
