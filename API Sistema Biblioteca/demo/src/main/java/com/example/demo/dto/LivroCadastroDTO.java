package com.example.demo.dto;

public record LivroCadastroDTO(
        String titulo,
        String autor,
        String isbn,
        Integer quantidade,
        String categoria
) {
}