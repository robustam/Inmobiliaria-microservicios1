package com.inmobiliaria.reservasservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // <--- IMPORTANTE

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // <--- ESTA ES LA LLAVE QUE FALTA
public class ReservasServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservasServiceApplication.class, args);
    }

}