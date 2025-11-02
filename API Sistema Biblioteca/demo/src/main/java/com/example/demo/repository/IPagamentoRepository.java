package com.example.demo.repository;

import com.example.demo.Entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;      // <-- ADICIONE ESTE IMPORT
import org.springframework.data.repository.query.Param; // <-- ADICIONE ESTE IMPORT
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT p FROM Pagamento p WHERE p.cliente.cliente_id = :clienteId")
    List<Pagamento> findByClienteId(@Param("clienteId") Long clienteId);

}