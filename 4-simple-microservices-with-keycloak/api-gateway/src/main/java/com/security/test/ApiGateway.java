package com.security.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGateway {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateway.class, args);
	}

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("internal-route", r -> r.path("/test")
                        .uri("forward:/test"))
                .route("admin-service", route ->
                        route.path("/admin/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:3001")
                )
                .route("user-service", route ->
                    route.path("/user/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:3002")
                )
                .build();
    }
}
