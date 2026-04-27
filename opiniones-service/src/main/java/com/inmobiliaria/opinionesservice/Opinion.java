package com.inmobiliaria.opinionesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // ¡Esta línea es la magia de la comunicación!
public class OpinionesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpinionesServiceApplication.class, args);
    }
}