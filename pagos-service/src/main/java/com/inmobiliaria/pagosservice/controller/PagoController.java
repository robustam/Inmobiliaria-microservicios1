package com.inmobiliaria.pagosservice.controller;

import com.inmobiliaria.pagosservice.model.Pago;
import com.inmobiliaria.pagosservice.service.PagoService.PagoService; // ✅ corregido
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public String procesarPago(@RequestBody Pago pago) {
        return pagoService.procesarPago(pago);
    }

    @GetMapping("/listar")
    public List<Pago> listarPagos() {
        return pagoService.listarTodos(); // Necesitas crear este método en el Service
    }

}