package com.example.demo.controller;

import com.example.demo.dto.EmprestimoDTO;
import com.example.demo.dto.EmprestimoResponseDTO;
import com.example.demo.service.EmprestimoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprestimos")
@AllArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDto) {
        EmprestimoResponseDTO novoEmprestimo = emprestimoService.criarEmprestimo(emprestimoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimos() {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarTodosEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EmprestimoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> request) {

        Boolean novoStatus = request.get("status");
        if (novoStatus == null) {
            return ResponseEntity.badRequest().build();
        }

        EmprestimoResponseDTO emprestimoAtualizado = emprestimoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(emprestimoAtualizado);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<EmprestimoResponseDTO>> consultarPorCliente(@PathVariable Long clienteId) {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.consultarEmprestimosPorCliente(clienteId);
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<EmprestimoResponseDTO>> consultarAtrasados() {
        List<EmprestimoResponseDTO> atrasados = emprestimoService.consultarEmprestimosAtrasados();
        return ResponseEntity.ok(atrasados);
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(@PathVariable Long id) {
        EmprestimoResponseDTO emprestimoDevolvido = emprestimoService.registrarDevolucao(id);
        return ResponseEntity.ok(emprestimoDevolvido);
    }
}