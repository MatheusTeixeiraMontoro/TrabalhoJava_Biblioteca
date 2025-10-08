package com.example.demo.repository;

import com.example.demo.Entities.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMultaRepository extends JpaRepository<Multa, Long> {

}
