package com.rydr.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service Registry and Discovery Server powered by Netflix Eureka.
 * All microservices in the Rydr ecosystem register with this server to discover peers dynamically.
 *
 * Default Port: 7900
 * @author Rydr Team
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}

