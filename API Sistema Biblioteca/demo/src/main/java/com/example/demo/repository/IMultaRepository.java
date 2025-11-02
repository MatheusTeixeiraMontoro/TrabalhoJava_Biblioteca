package com.example.demo.repository;

import com.example.demo.Entities.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMultaRepository extends JpaRepository<Multa, Long> {

    @Query("SELECT m FROM Multa m WHERE m.emprestimo.cliente.cliente_id = :clienteId")
    List<Multa> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT m FROM Multa m WHERE m.emprestimo.emprestimo_id = :emprestimoId")
    Optional<Multa> findByEmprestimoId(@Param("emprestimoId") Long emprestimoId);
}