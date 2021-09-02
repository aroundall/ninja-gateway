package org.amuji.ninjagateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements GatewayFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayFilter.class);

    @Autowired
    private TokenRetriever tokenRetriever;
    @Autowired
    private Credential credential;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("Retrieving the token...");
        String token = tokenRetriever.getToken(credential);

        exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.set("token", token))
                .build();
        LOGGER.info("Got the token");

        return chain.filter(exchange);
    }
}
