package com.example.demo.dto;
import java.time.LocalDateTime;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;


public record  ReservaDTO (Cliente clienteID, Livro livroId, LocalDateTime dataReserva, LocalDateTime dataDevolucao, boolean status){}