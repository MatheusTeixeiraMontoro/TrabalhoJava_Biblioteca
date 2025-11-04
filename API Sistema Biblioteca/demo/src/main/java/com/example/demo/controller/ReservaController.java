package com.example.demo.controller;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.dto.ReservaResponseDTO;
import com.example.demo.service.ReservaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Reserva", description = "Endpoints para gerenciamento de reservas")
@RestController
@RequestMapping("/api/reservas")
@AllArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;


    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaResponseDTO novaReserva = reservaService.criarReserva(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
        } catch (EntityNotFoundException e) {
            // Cliente ou Livro não encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            // Livro esgotado ou outra regra de negócio violada
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodasReservas() {
        List<ReservaResponseDTO> reservas = reservaService.listarTodasReservas();
        return ResponseEntity.ok(reservas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaPorId(@PathVariable Long id) {
        try {
            ReservaResponseDTO reserva = reservaService.buscarReservaPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservaResponseDTO> AtualizarStatusReserva(@PathVariable Long id) {
        try {
            ReservaResponseDTO reservaAtualizada = reservaService.AtualizarStatusReserva(id);
            return ResponseEntity.ok(reservaAtualizada);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            // Tentativa de reativar reserva sem estoque
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirReservaPorId(@PathVariable Long id) {
        try {
            reservaService.excluirReservaPorId(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}