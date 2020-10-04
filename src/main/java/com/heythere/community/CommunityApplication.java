package com.heythere.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableJpaAuditing
@SpringBootApplication
public class CommunityApplication {

    public static void main(String... args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
    @GetMapping("greeting/{name}")
    public String greeting(@PathVariable("name") final String name) {
        return "hi~ " + name + "!";
    }
}
