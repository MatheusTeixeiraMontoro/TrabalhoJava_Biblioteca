
package com.example.demo.controller;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.dto.ReservaResponseDTO;
import com.example.demo.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@AllArgsConstructor
public class ReservaController {

    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<String> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        return reservaService.criarReserva(reservaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> consultaListaReserva() {
        return ResponseEntity.ok(reservaService.consultaListaReserva());
    }

   @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaById(id));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirReserva(@PathVariable Long id) {
        return reservaService.excluirReserva(id);
    }
}