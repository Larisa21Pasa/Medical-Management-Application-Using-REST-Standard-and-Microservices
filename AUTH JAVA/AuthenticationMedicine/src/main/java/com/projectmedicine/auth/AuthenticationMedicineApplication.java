package com.projectmedicine.auth;

//import com.projectmedicine.auth.AuthenticationComponent.protoController.AuthenticationImplementation;
//import com.projectmedicine.auth.AuthenticationComponent.protoController.AuthenticationImplementation;
import com.projectmedicine.auth.AuthenticationComponent.protoController.AuthenticationImplementation;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
@Log4j2
@SpringBootApplication
public class AuthenticationMedicineApplication {

    public static void main(String[] args) {
//        try {
//            Server server = ServerBuilder.forPort(8083).addService(new AuthenticationImplementation()).build();
//
//            server.start();
//            System.out.println("Server started at " + server.getPort());
//            server.awaitTermination();
//        } catch (IOException e) {
//            System.out.println("Error: " + e);
//        } catch (InterruptedException e) {
//            System.out.println("Error: " + e);
//        }

        SpringApplication.run(AuthenticationMedicineApplication.class, args);
    }

    @Bean
    public Server grpcServer(AuthenticationImplementation authenticationImplementation) {
        Server server = ServerBuilder.forPort(8083)
                .addService(authenticationImplementation)
                .build();

        try {
            server.start();
            log.info("Server started at port {}", server.getPort());
          //  server.awaitTermination();
        } catch (IOException  e) {
            log.error("Error starting gRPC server", e);
        }

        return server;
    }

}
