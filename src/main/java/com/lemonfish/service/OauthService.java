package com.lemonfish.service;

import com.lemonfish.entity.User;
import me.zhyd.oauth.model.AuthUser;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.service
 * @date 2020/5/24 16:59
 */
public interface OauthService {
     void addUserToRankList(User user);

     User register(String oauthType, AuthUser authUser);
}
