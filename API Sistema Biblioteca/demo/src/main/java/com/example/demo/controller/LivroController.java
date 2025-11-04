package com.example.demo.controller;

import com.example.demo.Entities.Livro;
import com.example.demo.dto.LivroCadastroDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.service.LivroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Livro", description = "Endpoints para gerenciamento de livros")
@RestController
@RequestMapping("/api/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@Valid @RequestBody LivroCadastroDTO livroCadastroDTO) {
        LivroDTO livroSalvo = livroService.criarLivro(livroCadastroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        List<LivroDTO> todosOsLivros = livroService.listarTodosOsLivros();
        return ResponseEntity.ok(todosOsLivros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {

        LivroDTO livro = livroService.buscarLivroPorId(id);

        return ResponseEntity.ok(livro);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<LivroDTO>> listarLivrosDisponiveis() {
        List<LivroDTO> livrosDisponiveis = livroService.listarLivrosDisponiveis();

        return ResponseEntity.ok(livrosDisponiveis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO livroAtualizado = livroService.atualizarLivro(id, livroDTO);

        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        livroService.excluirLivro(id);

        return ResponseEntity.noContent().build();
    }

}
