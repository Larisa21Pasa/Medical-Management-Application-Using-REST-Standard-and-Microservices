package com.projectmedicine.gateway.gateway.Utils.Others;

import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.AuthenticationServiceGrpc;
import io.grpc.ManagedChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.grpc.ManagedChannelBuilder;
@Configuration
public class GrpcConfig {

    @Bean
    public ManagedChannel grpcChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8083)
                .usePlaintext()
                .build();
    }
}
