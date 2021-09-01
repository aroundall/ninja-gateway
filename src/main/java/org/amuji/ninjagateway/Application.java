package org.amuji.ninjagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouteLocator theRoutes(RouteLocatorBuilder builder, TokenGatewayFilterFactory tokenGatewayFilterFactory) {
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("hello", "world")
                                .filter(tokenGatewayFilterFactory.apply(c -> c.getClass())))
                        .uri("http://httpbin.org:80"))
                .build();
    }
}
