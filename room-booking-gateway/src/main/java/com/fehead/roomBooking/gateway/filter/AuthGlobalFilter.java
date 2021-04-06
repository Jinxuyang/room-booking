package com.fehead.roomBooking.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * @Author Verge
 * @Date 2021/3/24 13:48
 * @Version 1.0
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入 global filter");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            JSONObject payloadJSON = jwsObject.getPayload().toJSONObject();

            ServerHttpRequest request = exchange
                    .getRequest()
                    .mutate()
                    .header("user", userStr)
                    .build();
            if ("user".equals(payloadJSON.get("client_id"))){
                String userId = payloadJSON.get("user_id").toString();
                URI oldUri = exchange.getRequest().getURI();

                URI newUri = new URI(oldUri.getPath()+"?"+oldUri.getQuery()+"&userId="+userId);
                request = exchange
                        .getRequest()
                        .mutate()
                        .uri(newUri)
                        .build();
            }

            exchange = exchange.mutate().request(request).build();
        } catch (ParseException | URISyntaxException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
