package com.fehead.roomBooking.gateway.component;

import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Verge
 * @Date 2021/3/24 19:43
 * @Version 1.0
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        String oriUri = uri.getPath();

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String resUri;
        if (antPathMatcher.match("/api/v1/applications/*",oriUri)) resUri = "/api/v1/applications/*";
        else if (antPathMatcher.match("/api/v1/rooms/*/statuses",oriUri)) resUri = "/api/v1/rooms/*/statuses";
        else if (antPathMatcher.match("/api/v1/rooms/*/statuses/*",oriUri)) resUri = "/api/v1/rooms/*/statuses/*";
        else if (antPathMatcher.match("/api/v1/file/**",oriUri)) resUri = "/api/v1/file/**";
        else if (antPathMatcher.match("/api/v1/rooms/*",oriUri)) resUri = "/api/v1/rooms/*";

        else resUri = oriUri;

        Object obj = redisTemplate.opsForHash().get("AUTH:RESOURCE_ROLES_MAP", resUri);
        List<String> authorities = Convert.toList(String.class,obj);
        authorities = authorities.stream().map(i -> i = "ROLE_" + i).collect(Collectors.toList());

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(true));
    }
}
