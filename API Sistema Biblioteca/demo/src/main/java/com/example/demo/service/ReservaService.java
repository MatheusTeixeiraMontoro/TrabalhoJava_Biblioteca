package com.example.demo.service;

import com.example.demo.Entities.Reserva;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.dto.ReservaResponseDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.ILivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ILivroRepository livroRepository;

@Transactional
    public ResponseEntity<String> criarReserva(ReservaDTO reservaDTO) {
        Cliente cliente = clienteRepository.findById(reservaDTO.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Livro livro = livroRepository.findById(reservaDTO.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        Reserva reserva = new Reserva();

        reserva.setCliente(cliente);
        reserva.setLivro(livro);
        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(true);
        reserva.setDataDevolucao(reservaDTO.dataDevolucao());

        reservaRepository.save(reserva);
        return ResponseEntity.ok("Reserva criada com sucesso!");
    }

    public List<ReservaResponseDTO> consultaListaReserva(){
        List<Reserva> reservaResponse = reservaRepository.findAll();
        return reservaResponse
                .stream()
                .map(c-> new ReservaResponseDTO(
                        c.getReserva_id(),
                        c.isStatus(),
                        c.getDataReserva(),
                        c.getDataDevolucao(),

                        new ClienteDTO(
                                c.getCliente().getNome(),
                                c.getCliente().getEmail(),
                                c.getCliente().getTelefone(),
                                c.getCliente().getEndereco()
                        ),
                        new LivroDTO(
                                c.getLivro().getTitulo(),
                                c.getLivro().getAutor(),
                                c.getLivro().getQuantidade(),
                                c.getLivro().getCategoria()
                        )
                        // ---------------------

                ))
                .collect(Collectors.toList());
    }
}