package com.example.demo.dto;

public record LivroDTO(
        String titulo,
        String autor,
        Integer quantidade,
        String categoria
) {
}
