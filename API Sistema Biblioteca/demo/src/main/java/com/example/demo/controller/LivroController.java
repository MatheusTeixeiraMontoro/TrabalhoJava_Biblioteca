package com.example.demo.controller;

import com.example.demo.Entities.Livro;
import com.example.demo.dto.LivroDTO;
import com.example.demo.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody LivroDTO livroDTO) {
        Livro livroSalvo = livroService.criarLivro(livroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        List<LivroDTO> todosOsLivros = livroService.listarTodosOsLivros();
        return ResponseEntity.ok(todosOsLivros);
    }

}
