package com.lemonfish.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.entity.OauthMap;
import com.lemonfish.entity.User;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.mapper.OauthMapMapper;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.service.OauthService;
import com.lemonfish.util.MyJsonResult;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.lemonfish.enumcode.InfoEnum.LEMONC_USERINFO_PREFIX;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.controller
 * @date 2020/5/15 23:20
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    public static final int ONE_DAY_TIME = 3600 * 24;
     @Autowired
    private AuthRequestFactory factory;
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private  OauthMapMapper oauthMapMapper;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private  RedisUtil redisUtil;
    @Autowired
    private UserFactory userFactory;

    private final String  PREFIX = LEMONC_USERINFO_PREFIX.getName();

    /**
     * 登录,获取授权url
     *
     * @param oauthType 第三方登录类型
     */
    @GetMapping("/login/{oauthType}")
    public MyJsonResult renderAuth(@PathVariable String oauthType) {
        AuthRequest authRequest = factory.get(oauthType.toUpperCase());
        String authUrl = authRequest.authorize(AuthStateUtils.createState());
        System.out.println(authUrl);
        return MyJsonResult.success(authUrl);
    }

    /**
     * 登录成功后的回调
     *
     * @param oauthType 第三方登录类型
     * @param callback  携带返回的信息
     * @return 登录成功后的信息
     */
    @GetMapping("/{oauthType}/callback")
    public MyJsonResult login(@PathVariable String oauthType, AuthCallback callback,HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(oauthType.toUpperCase());

        // 返回用户信息
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        // 如果为空说明，认证失败
        if (authResponse == null) {
            return MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
        }
        AuthUser authUser = authResponse.getData();
        String uuidToken = UUID.randomUUID().toString().replace("-", "");




        // 查询映射关系
        // 第三方来源和用户id 是否存在数据库中
        LambdaQueryWrapper<OauthMap> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OauthMap::getOauthId, authUser.getUuid()).eq(OauthMap::getSource, oauthType);
        OauthMap oauthMapDb = oauthMapMapper.selectOne(lqw);

        // null 说明没有，即用户不存在
        if (oauthMapDb==null) {
            User user = oauthService.register(oauthType, authUser);
            oauthService.addUserToRankList(user);
            redisUtil.set(uuidToken, user, ONE_DAY_TIME);
        } else {
            // 从redis查找对应的用户
            User redisUser = userFactory.build(oauthMapDb.getUserId());
            if (redisUser != null) {
                // 登录的时候，需要把redis里面的用户信息重新存储到mysql里面
                userMapper.updateById(redisUser);
            }
            // 设置token存在时间
            redisUtil.set(uuidToken, redisUser, ONE_DAY_TIME);
        }

        // 重定向
        response.sendRedirect("http://localhost/?key=" + uuidToken);

        return MyJsonResult.success(authResponse);
    }




}
