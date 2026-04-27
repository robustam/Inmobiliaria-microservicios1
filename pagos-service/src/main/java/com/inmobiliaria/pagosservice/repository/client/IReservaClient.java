package com.inmobiliaria.pagosservice.repository.client;

import com.inmobiliaria.pagosservice.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservas-service")
public interface IReservaClient {

    @GetMapping("/api/reservas/{id}")
    ReservaDTO buscarReserva(@PathVariable("id") Long id);
}