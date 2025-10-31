package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        Long id,
        Long clienteId,
        String nomeCliente,
        BigDecimal valor,
        String formaPagamento,
        LocalDateTime dataPagamento,
        String status
) {
}