package com.zhisida.board.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
public class SysResourcesCache {

    @CachePut(cacheNames = "Board:System:Resources" , unless = " #result == null")
    public Set<String> putAllResources(Set<String> resources) {
        return resources;
    }

    @Cacheable(cacheNames = "Board:System:Resources", unless = " #result == null")
    public Set<String> getAllResources() {
        Set<String> urlSet = CollectionUtil.newHashSet();
        Map<String, RequestMappingHandlerMapping> mappingMap =
                SpringUtil.getApplicationContext().getBeansOfType(RequestMappingHandlerMapping.class);
        Collection<RequestMappingHandlerMapping> mappings = mappingMap.values();
        for (RequestMappingHandlerMapping mapping : mappings) {
            Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
            map.keySet().forEach(requestMappingInfo -> {
                Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                urlSet.addAll(patterns);
            });
        }
        return urlSet;
    }

}
