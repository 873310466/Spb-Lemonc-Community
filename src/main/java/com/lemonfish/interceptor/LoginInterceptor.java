package com.lemonfish.interceptor;

import com.alibaba.fastjson.JSON;
import com.lemonfish.entity.MyJsonResult;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.interceptor
 * @date 2020/5/4 20:25
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    public static final int ONE_HOUR = 3600;
    public static final int HLAF_DAY = 12 * ONE_HOUR;
    public static final String TOKEN = "token";

    @Autowired
    RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 从头部获取Token
        String token = request.getHeader(TOKEN);

        // 如果token为empty，就发送 `请先登录` 的标志给前端，并拦截请求
        // debug 发现如果是null，是字符串
        if (StringUtils.isEmpty(token) || "null".equals(token)) {
            sendJsonMessage(response, MyJsonResult.fail(CodeEnum.FAIL_NEED_LOGIN));
            return false;
        } else {
            // 请求头携带 token

            // 判断token 在不在 redis 里面
            boolean hasToken = redisUtil.hasKey(token);

            // 如果存在且token快过期，延长时效
            if (redisUtil.getExpire(token) < ONE_HOUR) {
                redisUtil.expire(token, ONE_HOUR);
            }

            // 如果token在redis里面，放行
            if (hasToken) {
                return true;
            } else {
                // 不在，传递`token无效或过期`给前端，并拦截该请求
                sendJsonMessage(response, MyJsonResult.fail(CodeEnum.FAIL_INVALID_TOKEN));
                return false;
            }
        }
    }

    /**
     * @Description 响应数据给前端
     *
     * @param response response
     * @param obj obj
     *
     * @return void
     * @author Lemonfish
     * @date 2020/5/4 20:32
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(obj));
        writer.flush();
        writer.close();
        response.flushBuffer();
    }
}
