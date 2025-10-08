package com.example.demo.repository;


import com.example.demo.Entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Long> {

}
