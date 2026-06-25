package com.rydr.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Passenger & Driver Wallet Balance Microservice Application.
 * Manages user wallet accounts, transaction histories, and balance updates.
 *
 * Default Port: 8006
 * @author Rydr Team
 */
@EnableEurekaClient
@SpringBootApplication
public class ServiceWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceWalletApplication.class, args);
    }
}

