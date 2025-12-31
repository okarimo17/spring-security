package com.security.test.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "gateways")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesConfig {
    private String adminRoute;
    private String userRoute;
}
