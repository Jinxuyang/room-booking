package com.fehead.roomBooking.oauth2.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author Verge
 * @Date 2021/3/24 19:38
 * @Version 1.0
 */
@Service
public class ResourceServiceImpl {
    private static final String PREFIX = "/api/v1";
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put(PREFIX+"/users", CollUtil.toList("user"));
        resourceRolesMap.put(PREFIX+"/rooms/*/statuses", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/rooms/*/statuses/*", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/applications", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/applications/*", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/rooms", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/rooms/*", CollUtil.toList("user","admin"));
        resourceRolesMap.put(PREFIX+"/file/**", CollUtil.toList("admin","user"));
        redisTemplate.opsForHash().putAll("AUTH:RESOURCE_ROLES_MAP", resourceRolesMap);
    }
}
