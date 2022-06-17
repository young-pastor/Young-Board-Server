
package com.zhisida.board.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.zhisida.board.core.cache.MappingCache;
import com.zhisida.board.core.cache.ResourceCache;
import com.zhisida.board.core.cache.UserCache;
import com.zhisida.board.core.redis.FastJson2JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.zhisida.board.core.pojo.login.SysLoginUser;

import java.util.Map;

/**
 * 缓存的配置，默认使用基于内存的缓存，如果分布式部署请更换为redis
 *
 * @author young-pastor
 */
@Configuration
public class CacheConfig {

    /**
     * url资源的缓存，默认不过期
     *
     * @author young-pastor
     */
    @Bean
    public ResourceCache resourceCache() {
        return new ResourceCache();
    }

    /**
     * 登录用户的缓存，redis缓存
     *
     * @author young-pastor
     */
    @Bean
    public UserCache userCache(RedisTemplate<String, SysLoginUser> redisTemplate) {
        return new UserCache(redisTemplate);
    }

    /**
     * redis缓存类
     *
     * @author young-pastor
     */
    @Bean
    public RedisTemplate<String, SysLoginUser> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, SysLoginUser> userRedisTemplate = new RedisTemplate<>();
        userRedisTemplate.setConnectionFactory(factory);
        userRedisTemplate.setKeySerializer(new StringRedisSerializer());
        userRedisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<>(SysLoginUser.class));
        userRedisTemplate.afterPropertiesSet();
        return userRedisTemplate;
    }

    /**
     * mapping映射缓存
     *
     * @author young-pastor
     */
    @Bean
    public MappingCache mappingCache() {
        TimedCache<String, Map<String, Object>> timedCache =
                CacheUtil.newTimedCache(2 * 60 * 1000);

        // 定时清理缓存，间隔1秒
        timedCache.schedulePrune(1000);

        return new MappingCache(timedCache);
    }

}
