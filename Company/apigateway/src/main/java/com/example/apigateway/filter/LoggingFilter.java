package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilter {

    @Bean
    public GlobalFilter logRequest() {
        return (exchange, chain) -> {

            System.out.println("Incoming Request: "
                    + exchange.getRequest().getMethod()
                    + " "
                    + exchange.getRequest().getURI());

            return chain.filter(exchange);
        };
    }
}