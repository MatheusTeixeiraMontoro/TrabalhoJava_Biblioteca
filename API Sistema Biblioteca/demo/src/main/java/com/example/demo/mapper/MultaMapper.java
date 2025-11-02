package com.example.demo.mapper;

import com.example.demo.Entities.Multa;
import com.example.demo.dto.MultaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MultaMapper {

    @Mapping(source = "multa_id", target = "id")
    @Mapping(source = "emprestimo.emprestimo_id", target = "emprestimoId")
    @Mapping(source = "emprestimo.cliente.cliente_id", target = "clienteId")
    @Mapping(source = "emprestimo.cliente.nome", target = "nomeCliente")
    @Mapping(source = "emprestimo.livro.titulo", target = "tituloLivro")
    MultaResponseDTO toResponseDTO(Multa multa);
}