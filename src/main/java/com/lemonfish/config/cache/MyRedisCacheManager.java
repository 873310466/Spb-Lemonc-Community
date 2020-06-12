package com.lemonfish.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.config.cache
 * @date 2020/5/27 11:36
 */
@Configuration
@EnableCaching
public class MyRedisCacheManager {
    /********************以下代码解决@Cacheable不支持设置缓存过期的问题**************************/
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)); // 默认没有特殊指定的

        // 针对不同cacheName，设置不同的过期时间
        Map<String, RedisCacheConfiguration> initialCacheConfiguration = new HashMap<String, RedisCacheConfiguration>(); /*{{
            put("demoCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1))); //1小时
            put("demoCar", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))); // 10分钟
            // ...
        }};*/

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(defaultCacheConfig) // 默认配置（强烈建议配置上）。  比如动态创建出来的都会走此默认配置
                .withInitialCacheConfigurations(initialCacheConfiguration) // 不同cache的个性化配置
                .build();
        return redisCacheManager;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setDatabase(0);
        return new LettuceConnectionFactory(configuration);
    }


}
