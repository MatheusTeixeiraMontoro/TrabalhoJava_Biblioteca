package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MultaResponseDTO(
        Long id,
        Long emprestimoId,
        Long clienteId,
        String nomeCliente,
        String tituloLivro,
        BigDecimal valor,
        String status,
        LocalDateTime dataPagamento
) {
}