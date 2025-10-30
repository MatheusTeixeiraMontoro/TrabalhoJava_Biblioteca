package com.example.demo.dto;

import java.time.LocalDateTime;

public record ManutencaoCadastroDTO(
        Long livroId,
        String descricao,
        LocalDateTime dataInicio
) {
}
