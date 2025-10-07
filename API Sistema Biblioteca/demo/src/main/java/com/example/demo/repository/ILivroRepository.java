package com.example.demo.repository;

import com.example.demo.Entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILivroRepository extends JpaRepository<Livro, Long> {

}