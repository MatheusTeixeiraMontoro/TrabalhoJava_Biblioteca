package com.example.demo.controller;

import com.example.demo.dto.EmprestimoDTO;
import com.example.demo.service.EmprestimoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
@AllArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<String> criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDto) {
        return emprestimoService.criarEmprestimo(emprestimoDto);
    }

}

