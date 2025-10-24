package com.example.demo.dto;
import java.time.LocalDateTime;

public record EmprestimoDTO(
        Long clienteId,
        Long livroId,
        LocalDateTime dataDevolucao) {

}
