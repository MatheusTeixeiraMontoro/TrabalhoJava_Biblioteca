package com.example.demo.service;

import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.repository.IReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservaService {

    public IReservaRepository reservaRepository;

     public ResponseEntity salvarReserva(ReservaDTO reservaDTO){

         Reserva reserva = new Reserva();

         reserva.setDataReserva(reservaDTO.dataReserva());
         reserva.setDataDevolucao(reservaDTO.dataDevolucao());
         reserva.setStatus(reservaDTO.status());
         reserva.setClienteId(reservaDTO.clienteID()); // Exemplo de campo
         reserva.setLivroId(reservaDTO.livroId());

         reservaRepository.save(reserva);
         return ResponseEntity.ok().build();

     }

     public List<ReservaDTO> consultarReservas(){

         List<Reserva> reservas = reservaRepository.findAll();

         return reservas.stream()
                 .map(reserva -> new ReservaDTO(
                         reserva.getClienteId(),       // 1. Cliente
                         reserva.getLivroId(),         // 2. Livro
                         reserva.getDataReserva(),   // 3. LocalDateTime
                         reserva.getDataDevolucao(), // 4. LocalDateTime
                         reserva.getStatus()         // 5. String
                 ))
                 .collect(Collectors.toList());


     }

    public ReservaDTO consultaReservaById(Long id){
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva nao encontrada"));

        // Mapeia usando o construtor correto
        return new ReservaDTO(
                reserva.getClienteId(),       // 1. Cliente
                reserva.getLivroId(),         // 2. Livro
                reserva.getDataReserva(),   // 3. LocalDateTime
                reserva.getDataDevolucao(), // 4. LocalDateTime
                reserva.getStatus()         // 5. String
        );
     }
}
