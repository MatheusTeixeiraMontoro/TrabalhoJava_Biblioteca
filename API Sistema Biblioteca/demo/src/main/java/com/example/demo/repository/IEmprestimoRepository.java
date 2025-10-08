package com.example.demo.repository;

import com.example.demo.Entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}
