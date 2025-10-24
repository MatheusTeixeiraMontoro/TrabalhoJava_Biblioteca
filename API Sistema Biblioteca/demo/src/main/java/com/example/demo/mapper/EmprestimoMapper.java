package com.example.demo.mapper;

import com.example.demo.Entities.Emprestimo;
import com.example.demo.Entities.Usuario;
import com.example.demo.dto.EmprestimoDTO;
import com.example.demo.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {


    EmprestimoDTO toDTO(Emprestimo emprestimo);

    Emprestimo toEntity(EmprestimoDTO emprestimoDTO);

    List<EmprestimoDTO> toDTOList(List<Emprestimo> emprestimos);
}
