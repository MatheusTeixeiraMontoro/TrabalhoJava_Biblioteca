package com.example.demo.mapper;

import com.example.demo.Entities.Emprestimo;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.EmprestimoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(source = "emprestimo_id", target = "emprestimo_id")
    @Mapping(source = "dataEmprestimo", target = "dataEmprestimo")
    @Mapping(source = "dataDevolucao", target = "dataDevolucao")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "cliente", target = "cliente", qualifiedByName = "toClienteDTO")
    @Mapping(source = "livro", target = "livro", qualifiedByName = "toLivroDTO")
    EmprestimoResponseDTO toResponse(Emprestimo emprestimo);

    @Named("toClienteDTO")
    default ClienteDTO toClienteDTO(Cliente cliente) {
        if (cliente == null) return null;

        return new ClienteDTO(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco()
        );
    }

    @Named("toLivroDTO")
    default LivroDTO toLivroDTO(Livro livro) {
        if (livro == null) return null;

        // CORREÇÃO AQUI: Adicionado o livro_id como primeiro argumento
        return new LivroDTO(
                livro.getLivro_id(), // <<< Argumento que estava faltando
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getQuantidade(),
                livro.getCategoria()
        );
    }
}