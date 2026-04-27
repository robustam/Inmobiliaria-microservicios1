package com.inmobiliaria.pagosservice.controller;

import com.inmobiliaria.pagosservice.model.Pago;
import com.inmobiliaria.pagosservice.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public String procesarPago(@RequestBody Pago pago) {
        return pagoService.procesarPago(pago);
    }
}