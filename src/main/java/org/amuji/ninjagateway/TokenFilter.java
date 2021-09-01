package org.amuji.ninjagateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements GatewayFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayFilter.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("Retrieving the token...");
        ResponseEntity<String> token = restTemplate.getForEntity("http://www.baidu.com", String.class);
        LOGGER.info("Got the token");
        return chain.filter(exchange);
    }
}
