package com.example.demo.repository;

import com.example.demo.Entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {


    @Query("SELECT e FROM Emprestimo e WHERE e.cliente.cliente_id = :clienteId")
    List<Emprestimo> findEmprestimosByClienteId(@Param("clienteId") Long clienteId);


    @Query("SELECT e FROM Emprestimo e WHERE e.status = true AND e.dataDevolucao < :dataAtual")
    List<Emprestimo> findEmprestimosAtrasados(@Param("dataAtual") LocalDateTime dataAtual);
}