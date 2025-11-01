package com.example.demo.mapper;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.ReservaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Gera um Bean Spring
public interface ReservaMapper {

    @Mapping(source = "cliente", target = "cliente")
    @Mapping(source = "livro", target = "livro")
    ReservaResponseDTO toResponseDTO(Reserva reserva);


    ClienteDTO toClienteDTO(Cliente cliente);


    LivroDTO toLivroDTO(Livro livro);
}