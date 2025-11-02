package com.example.demo.controller;

import com.example.demo.dto.MultaResponseDTO;
import com.example.demo.service.MultaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multas")
@AllArgsConstructor
public class MultaController {

    private final MultaService multaService;

    @PostMapping("/verificar")
    public ResponseEntity<List<MultaResponseDTO>> verificarEGerarMultas() {
        List<MultaResponseDTO> multasPendentes = multaService.verificarEGerarMultas();
        return ResponseEntity.ok(multasPendentes);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MultaResponseDTO>> consultarMultasPorCliente(@PathVariable Long clienteId) {
        List<MultaResponseDTO> multas = multaService.consultarMultasPorCliente(clienteId);
        return ResponseEntity.ok(multas);
    }

    @PatchMapping("/{multaId}/pagar")
    public ResponseEntity<MultaResponseDTO> pagarMulta(@PathVariable Long multaId) {
        MultaResponseDTO multaPaga = multaService.pagarMulta(multaId);
        return ResponseEntity.ok(multaPaga);
    }
}