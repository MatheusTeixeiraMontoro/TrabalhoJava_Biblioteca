package com.example.demo.controller;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.dto.ReservaResponseDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/reservas")
@AllArgsConstructor
public class ReservaController {

    private ReservaService reservaService;

    @PostMapping
=    public ResponseEntity cadastrarReservas(@RequestBody ReservaDTO reservaDTO) {

        return reservaService.criarReserva(reservaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok().body(reservaService.consultaListaReserva());
    }
}
