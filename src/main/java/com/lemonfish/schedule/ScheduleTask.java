package com.lemonfish.schedule;

import com.lemonfish.entity.User;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Arg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.schedule
 * @date 2020/5/27 13:10
 */
@Component
@Slf4j
public class ScheduleTask {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 文章浏览量更新
     */
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(fixedRate = 1000*60)
    public void updateArticleViewCount() {
        log.info("计数君开始");
        Set<Long> ids = articleMapper.selectAllId();
        Map<Long, Integer> map = new HashMap<>(ids.size());

        ids.forEach(id -> {
            String key = InfoEnum.VIEW_ARTICLE.getName() + id;
            int views = redisUtil.pfCount(key);
            if (views > 0) {
                map.put(id, views);
                redisUtil.del(key);
            }
        });

        if (map.size() > 0) {
            articleMapper.updateAllViewCount(map);
        }


        log.info("计数君结束");
    }

/*    *//**
     * 用户信息更新
     *//*
    @Scheduled(fixedRate = 1000*60)
    public void updateUserInfo() {
        log.info("更新用户信息开始");
        Set<Long> ids = articleMapper.selectAllId();
        Map<Long, Integer> map = new HashMap<>(ids.size());

        ids.forEach(id -> {
            String key = InfoEnum.LEMONC_USERINFO_PREFIX.getName() + id;
            User user = (User) redisUtil.get(key);
            int views = redisUtil.pfCount(key);
            if (views > 0) {
                map.put(id, views);
                redisUtil.del(key);
            }
        });
        if (map.size() > 0) {
            articleMapper.updateAllViewCount(map);
        }

        log.info("更新用户信息结束");

    }*/
}
