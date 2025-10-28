package com.example.demo.controller;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/reservas")
@AllArgsConstructor
public class ReservaController {

    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity cadastrarReservas(@RequestBody ReservaDTO reservaDTO) {
        reservaService.criarReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaDTO);
    }
}
