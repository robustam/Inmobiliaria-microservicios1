package com.inmobiliaria.mantenimientoservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication @EnableDiscoveryClient @EnableFeignClients
public class MantenimientoServiceApplication {
    public static void main(String[] args) { SpringApplication.run(MantenimientoServiceApplication.class, args); }
}
