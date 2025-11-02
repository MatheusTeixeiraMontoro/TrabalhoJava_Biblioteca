package com.example.demo.dto;

import java.time.LocalDateTime;


public record ReservaResponseDTO(
        Long reserva_id,
        boolean status,
        LocalDateTime dataReserva,
        LocalDateTime dataDevolucao,
        ClienteDTO cliente,
        LivroDTO livro
) {}