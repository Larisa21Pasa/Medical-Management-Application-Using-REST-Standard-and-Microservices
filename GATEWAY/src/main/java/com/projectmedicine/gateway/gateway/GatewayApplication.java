package com.projectmedicine.gateway.gateway;

import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
    @Autowired
    private ManagedChannel grpcChannel;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
