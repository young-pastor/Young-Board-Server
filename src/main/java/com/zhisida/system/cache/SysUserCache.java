
package com.zhisida.system.cache;

import com.zhisida.core.pojo.login.SysLoginUser;
import com.zhisida.core.util.RedisUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录用户的缓存，存储了当前登录的用户
 * <p>
 * 一般用于在线用户的查看和过滤器检测用户是否登录
 * <p>
 * key为用户的唯一id，value为LoginUser对象
 *
 * @author Young-Pastor
 */
@Component
public class SysUserCache {
    @Resource
    RedisUtil redisUtil;

    @Cacheable(cacheNames = "Board:System:Login:User", key = "#userKey", unless = " #result == null")
    public SysLoginUser get(String userKey) {
        return null;
    }

    @CacheEvict(cacheNames = "Board:System:Login:User", key = "#userKey")
    public void remove(String userKey) {
    }

    public Map<String, SysLoginUser> getAllKeyValues() {
        Map<String, SysLoginUser> map = new HashMap<>();
        Set<String> userKeys = redisUtil.getInstance().keys("Board:System:Login:User*");
        userKeys.forEach((e) -> {
            map.put(e, (SysLoginUser) redisUtil.get(e));
        });
        return map;
    }

    @Cacheable(cacheNames = "Board:System:Login:User", key = "#userKey", unless = " #result == null")
    public SysLoginUser put(String userKey, SysLoginUser sysLoginUser, Long toLong) {
        return sysLoginUser;
    }
}
