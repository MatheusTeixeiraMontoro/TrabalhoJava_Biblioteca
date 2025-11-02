package com.example.demo.service;

import com.example.demo.Entities.Reserva;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.dto.ReservaResponseDTO;
import com.example.demo.mapper.ReservaMapper; // 1. IMPORTAR O MAPPER
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.ILivroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservaService {

    private final IReservaRepository reservaRepository;
    private final IClienteRepository clienteRepository;
    private final ILivroRepository livroRepository;
    private final ReservaMapper reservaMapper;

    @Transactional
    public ReservaResponseDTO criarReserva(ReservaDTO reservaDTO) {
        Cliente cliente = clienteRepository.findById(reservaDTO.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + reservaDTO.clienteId()));

        Livro livro = livroRepository.findById(reservaDTO.livroId())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + reservaDTO.livroId()));

        if (livro.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Livro '" + livro.getTitulo() + "' está esgotado. Não é possível reservar.");
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepository.save(livro);

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setLivro(livro);
        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(true);
        reserva.setDataDevolucao(reservaDTO.dataDevolucao());

        Reserva reservaSalva = reservaRepository.save(reserva);

        return reservaMapper.toResponseDTO(reservaSalva);
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarTodasReservas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + id));

        return reservaMapper.toResponseDTO(reserva);
    }

    @Transactional
    public ReservaResponseDTO AtualizarStatusReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + id));

        Livro livro = reserva.getLivro();
        int quantidadeAtual = livro.getQuantidade();

        if (reserva.isStatus()) {
            livro.setQuantidade(quantidadeAtual + 1);
            reserva.setStatus(false);
        } else {
            if (quantidadeAtual <= 0) {
                throw new IllegalArgumentException("Não é possível reativar a reserva. Livro com estoque esgotado.");
            }
            livro.setQuantidade(quantidadeAtual - 1);
            reserva.setStatus(true);
        }

        livroRepository.save(livro);
        Reserva reservaAtualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponseDTO(reservaAtualizada);
    }

    @Transactional
    public void excluirReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + id));

        if (reserva.isStatus()) {
            Livro livro = reserva.getLivro();
            livro.setQuantidade(livro.getQuantidade() + 1);
            livroRepository.save(livro);
        }

        reservaRepository.delete(reserva);
    }


}