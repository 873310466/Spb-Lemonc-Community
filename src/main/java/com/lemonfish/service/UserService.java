package com.lemonfish.service;

import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.dto.UserListDTO;
import com.lemonfish.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
  * <p>
  *  服务类
  * </p>
  *
  * @author LemonFish
  * @since 2020-05-06
  */
public interface UserService extends IService<User> {
    /**
     * 根据自定义的token(UUID)获取用户信息
     * @param token
     * @return
     */
    User getUserInfoByToken(String token);

    User getUserInfoById(Long id);

    boolean updateUserInfo(User user);


    /**
     * 获取关于文章的个人成就
     *
     * @param id
     * @return
     */
    UserAchiDTO getAchievement(Long id);

    List<UserListDTO> getViewList();

    List<UserListDTO> getLikeList();

    List<UserListDTO> getCollectionList();

}
