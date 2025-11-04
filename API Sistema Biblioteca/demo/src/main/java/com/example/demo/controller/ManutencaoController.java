package com.example.demo.controller;

import com.example.demo.dto.ManutencaoCadastroDTO;
import com.example.demo.dto.ManutencaoResponseDTO;
import com.example.demo.service.ManutencaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Manutenção", description = "Endpoints para gerenciamento da manutenção dos livros")
@RestController
@RequestMapping("/api/manutencoes")
@AllArgsConstructor
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    @PostMapping
    public ResponseEntity<ManutencaoResponseDTO> registrarManutencao(@RequestBody ManutencaoCadastroDTO dto) {
        ManutencaoResponseDTO response = manutencaoService.registrarManutencao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/andamento")
    public ResponseEntity<List<ManutencaoResponseDTO>> listarEmAndamento() {
        List<ManutencaoResponseDTO> response = manutencaoService.listarEmAndamento();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<ManutencaoResponseDTO> concluirManutencao(@PathVariable Long id) {
        ManutencaoResponseDTO response = manutencaoService.concluirManutencao(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<ManutencaoResponseDTO>> consultarHistoricoPorLivro(@PathVariable Long livroId) {
        List<ManutencaoResponseDTO> response = manutencaoService.consultarHistoricoPorLivro(livroId);
        return ResponseEntity.ok(response);
    }
}
