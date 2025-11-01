package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroDTO(
        Long livro_id,
        String titulo,
        String autor,
        String isbn,
        Integer quantidade,
        String categoria
) {

}
