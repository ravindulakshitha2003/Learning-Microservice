package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayLoggingFilter {

    @Bean
    public GlobalFilter logRoute() {
        return (exchange, chain) -> {

            System.out.println("Incoming URL: " + exchange.getRequest().getURI());

            Object route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

            System.out.println("Route: " + route);

            return chain.filter(exchange);
        };
    }
}