package com.fehead.roomBooking.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author Verge
 * @Date 2021/3/24 21:12
 * @Version 1.0
 */
@Configuration
@Slf4j
public class LogGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(exchange.getRequest().getURI().toString()+"进入LogGlobalFilter");
        //log.info(exchange.getRequest().getBody().toString());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
