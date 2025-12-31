package com.security.test;

import com.security.test.config.RoutesConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class ApiGateway {

    @Autowired
    private RoutesConfig routesConfig;

	public static void main(String[] args) {
		SpringApplication.run(ApiGateway.class, args);
	}


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("internal-route", r -> r.path("/test")
                        .uri("forward:/test"))
                .route("admin-service", route ->
                        route.path("/admin/**").filters(f -> f.stripPrefix(1)).uri(routesConfig.getAdminRoute())
                )
                .route("user-service", route ->
                    route.path("/user/**").filters(f -> f.stripPrefix(1)).uri(routesConfig.getUserRoute())
                )
                .build();
    }
}
