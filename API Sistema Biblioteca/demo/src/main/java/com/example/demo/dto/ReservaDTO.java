package com.example.demo.dto;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long clienteId,
        Long livroId,
        LocalDateTime dataDevolucao
) {}