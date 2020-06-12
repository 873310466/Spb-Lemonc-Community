package com.lemonfish.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.UserAchiDTO;
import com.lemonfish.dto.UserListDTO;
import com.lemonfish.entity.User;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.exception.BaseException;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  * <p>
 *  *  服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-06
 *  
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    public static final String LEMONC_PREFIX = "LEMONC:";
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserFactory userFactory;

    /**
     * 根据UUID的token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public User getUserInfoByToken(String token) {
        boolean result = redisUtil.hasKey(token);
        if (result) {
            return (User) redisUtil.get(token);
        }
        return null;
    }

    @Override
    public User getUserInfoById(Long id) {
        User user = userFactory.build(id);
        // 如果为null，说明不存在
        if (user == null) {
            throw new BaseException(CodeEnum.ACCOUNT_NOT_EXISTED);
        }
        return user;
    }

    @Override
    public boolean updateUserInfo(User user) {
        ValidateUtils.validateId(user.getId());

        if (StringUtils.isEmpty(user.getName())) {
            throw new BaseException(CodeEnum.NAME_NEEDED);
        }



        User userInRedis = userFactory.build(user.getId());
        // 如果前端传递过来的邮箱和redis里面的邮箱不相等，说明修改了邮箱，否则不管
        if (!userInRedis.getEmail().equals(user.getEmail())) {
            // 否则检测是否重复
            // 先排除自己，再查是否有相同的邮箱
            Integer count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getEmail, user.getEmail()).ne(User::getId, user.getId()));
            if (count > 0) {
                throw new BaseException(CodeEnum.DUMPLICATED_EMAIL);
            }
        }
        // 更新mysql
        int i = userMapper.updateById(user);

        BeanUtils.copyProperties(user, userInRedis);
        // 更新redis缓存
        userFactory.set(userInRedis);


        return i > 0;
    }

    /**
     * 获取用户成就
     *
     * @param id
     * @return
     */
    @Override
    public UserAchiDTO getAchievement(Long id) {
        ValidateUtils.validateId(id);

        UserAchiDTO userAchievement = userMapper.getUserAchievement(id);
        if (userAchievement != null) {
            userAchievement.setCli(
                    userAchievement.getLikeCount() +
                            userAchievement.getCollectCount() * 0.1 +
                            userAchievement.getCommentCount() * 0.05 +
                            userAchievement.getViewCount() * 0.01);
        } else {
            userAchievement = new UserAchiDTO()
                    .setCli(0d)
                    .setCommentCount(0)
                    .setCollectCount(0)
                    .setLikeCount(0)
                    .setViewCount(0);
        }


        return userAchievement;
    }

    @Override
    public List<UserListDTO> getViewList() {
        // 先选出redis zset 里面的数据
        Set<ZSetOperations.TypedTuple<Object>> viewListInRedis = redisUtil.zRangeWithScoreDesc(InfoEnum.VIEW_LIST.getName(), 0, 2);
        // 然后根据key 去获取redis里面存储的用户信息，进行  UserViewListDTO 的装配
        List<UserListDTO> viewList = viewListInRedis.stream().map(item ->
                new UserListDTO()
                        .setUser((User) redisUtil.get(LEMONC_PREFIX + item.getValue()))
                        .setCount(item.getScore().intValue())).collect(Collectors.toList());
        return viewList;
    }

    @Override
    public List<UserListDTO> getLikeList() {
        // 先选出redis zset 里面的数据
        Set<ZSetOperations.TypedTuple<Object>> likeListInRedis = redisUtil.zRangeWithScoreDesc(InfoEnum.LIKE_LIST.getName(), 0, 2);
        // 然后根据key 去获取redis里面存储的用户信息，进行  UserViewListDTO 的装配
        List<UserListDTO> likeList = likeListInRedis.stream().map(item ->
                new UserListDTO()
                        .setUser((User) redisUtil.get(LEMONC_PREFIX + item.getValue()))
                        .setCount(item.getScore().intValue())).collect(Collectors.toList());
        return likeList;
    }

    @Override
    public List<UserListDTO> getCollectionList() {
        // 先选出redis zset 里面的数据
        Set<ZSetOperations.TypedTuple<Object>> likeListInRedis = redisUtil.zRangeWithScoreDesc(InfoEnum.COLLECTION_LIST.getName(), 0, 2);
        // 然后根据key 去获取redis里面存储的用户信息，进行  UserViewListDTO 的装配
        List<UserListDTO> collectionList = likeListInRedis.stream().map(item ->
                new UserListDTO()
                        .setUser((User) redisUtil.get(LEMONC_PREFIX + item.getValue()))
                        .setCount(item.getScore().intValue())).collect(Collectors.toList());
        return collectionList;
    }


}


