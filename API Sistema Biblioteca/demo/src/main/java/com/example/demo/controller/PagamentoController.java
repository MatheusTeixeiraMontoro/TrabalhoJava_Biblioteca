package com.example.demo.controller;

import com.example.demo.dto.PagamentoCadastroDTO;
import com.example.demo.dto.PagamentoResponseDTO;
import com.example.demo.dto.PagamentoStatusUpdateDTO;
import com.example.demo.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> registrarPagamento(@RequestBody PagamentoCadastroDTO dto) {
        PagamentoResponseDTO response = pagamentoService.registrarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagamentoResponseDTO>> consultarPagamentosPorCliente(@PathVariable Long clienteId) {
        List<PagamentoResponseDTO> response = pagamentoService.consultarPagamentosPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PagamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestBody PagamentoStatusUpdateDTO dto) {
        PagamentoResponseDTO response = pagamentoService.atualizarStatus(id, dto);
        return ResponseEntity.ok(response);
    }
}