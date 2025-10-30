package com.example.demo.dto;

import java.time.LocalDateTime;

public record ManutencaoResponseDTO(
        Long id,
        Long livroId,
        String tituloLivro,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino,
        String status
) {
}
