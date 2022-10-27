package com.aa.fleet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(info = @Info(title = "Parking System API", version = "1.0", description = "Automated Parking System Design"))
public class AutomatedParkingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomatedParkingSystemApplication.class, args);
    }
}
