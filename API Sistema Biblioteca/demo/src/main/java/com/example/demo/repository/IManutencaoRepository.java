package com.example.demo.repository;

import com.example.demo.Entities.Livro;
import com.example.demo.Entities.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IManutencaoRepository extends JpaRepository<Manutencao, Long> {

    List<Manutencao> findByStatus(String status);

    @Query("SELECT m FROM Manutencao m WHERE m.livro.livro_id = :livroId")
    List<Manutencao> findByLivroId(@Param("livroId") Long livroId);

}
