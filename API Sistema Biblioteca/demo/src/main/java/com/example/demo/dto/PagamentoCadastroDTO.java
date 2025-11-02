package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoCadastroDTO(
        Long clienteId,
        BigDecimal valor,
        String formaPagamento,
        LocalDateTime dataPagamento
) {
}