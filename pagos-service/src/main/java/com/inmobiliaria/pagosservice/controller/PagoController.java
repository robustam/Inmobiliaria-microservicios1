package com.inmobiliaria.pagosservice.controller;

import com.inmobiliaria.pagosservice.model.Pago;
import com.inmobiliaria.pagosservice.service.PagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired private PagoService pagoService;

    @PostMapping("/procesar")
    public String procesarPago(@RequestBody Pago pago) {
        log.info("POST /api/pagos/procesar - Reserva: {}, Monto: {}", pago.getIdReserva(), pago.getMonto());
        String resultado = pagoService.procesarPago(pago);
        if (resultado.contains("Error")) {
            log.error("Error al procesar pago: {}", resultado);
        } else {
            log.info("Pago procesado exitosamente: {}", resultado);
        }
        return resultado;
    }

    @GetMapping("/listar")
    public List<Pago> listarPagos() {
        log.info("GET /api/pagos/listar - Listando pagos");
        List<Pago> pagos = pagoService.listarTodos();
        log.debug("Total pagos: {}", pagos.size());
        return pagos;
    }
}
