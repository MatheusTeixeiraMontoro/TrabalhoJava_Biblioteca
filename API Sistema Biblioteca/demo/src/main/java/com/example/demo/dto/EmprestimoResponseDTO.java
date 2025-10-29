package com.example.demo.dto;

import java.time.LocalDateTime;

public record EmprestimoResponseDTO (
        Long emprestimo_id,
        boolean status,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao,
        ClienteDTO cliente,
        LivroDTO livro
){
}