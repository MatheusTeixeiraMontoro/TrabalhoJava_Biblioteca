package com.example.demo.dto;
import java.time.LocalDateTime;

// Garanta que o nome do campo seja este:
public record EmprestimoDTO(
        Long clienteId,
        Long livroId,
        LocalDateTime dataDevolucaoPrevista // <-- O nome correto é este
) {

}