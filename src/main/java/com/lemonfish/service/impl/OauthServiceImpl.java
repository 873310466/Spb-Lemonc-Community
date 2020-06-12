package com.lemonfish.service.impl;

import com.alibaba.fastjson.JSON;
import com.lemonfish.entity.Collection;
import com.lemonfish.entity.OauthMap;
import com.lemonfish.entity.User;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.mapper.CollectionMapper;
import com.lemonfish.mapper.OauthMapMapper;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.service.OauthService;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

import static com.lemonfish.enumcode.InfoEnum.LEMONC_USERINFO_PREFIX;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.service.impl
 * @date 2020/5/24 16:58
 */
@Service
@Transactional
public class OauthServiceImpl implements OauthService {
    public static final int ONE_WEEK_TIME = 3600 * 24 * 7;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private OauthMapMapper oauthMapMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private UserFactory userFactory;

    /**
     * 将用户添加入排行榜
     *
     * @param user
     */
    @Override
    public void addUserToRankList(User user) {
        redisUtil.zAdd(InfoEnum.VIEW_LIST.getName(), InfoEnum.USER_ID_PREFIX.getName() + user.getId(), 0);
        redisUtil.zAdd(InfoEnum.LIKE_LIST.getName(), InfoEnum.USER_ID_PREFIX.getName() + user.getId(), 0);
        redisUtil.zAdd(InfoEnum.COLLECTION_LIST.getName(), InfoEnum.USER_ID_PREFIX.getName() + user.getId(), 0);
    }

    @Override
    public User register(String oauthType, AuthUser authUser) {
        // 因为要设置createdTime，而该属性是父类的，IDEA提示需要强转 迷惑
        User user = new User()
                .setName(authUser.getNickname())
                .setBio(authUser.getRemark())
                .setEmail("")
                .setAvatarUrl(authUser.getAvatar());
        // 添加进入数据库
        userMapper.insert(user);
        System.out.println(JSON.toJSONString(user));
        // 添加进入redis,时间半个月
        userFactory.set(user);
        // 添加映射
        OauthMap oauthMap = new OauthMap();
        oauthMap.setOauthId(Long.parseLong(authUser.getUuid()))
                .setSource(oauthType)
                .setUserId(user.getId());
        oauthMapMapper.insert(oauthMap);

        // 添加默认收藏夹
        collectionMapper.insert(new Collection()
                .setName("默认收藏夹君")
                .setDetail("这个收藏夹君是用来做什么的呢 ?")
                .setUserId(user.getId())
                .setCount(0));
        return user;
    }


}
