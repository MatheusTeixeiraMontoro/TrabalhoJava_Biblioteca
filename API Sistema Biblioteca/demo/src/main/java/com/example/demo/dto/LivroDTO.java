package com.example.demo.dto;

public record LivroDTO(
        String titulo,
        String autor,
        String isbn,
        Integer quantidade,
        String categoria
) {
}
