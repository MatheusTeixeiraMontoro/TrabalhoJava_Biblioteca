package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Pagamento;
import com.example.demo.dto.PagamentoCadastroDTO;
import com.example.demo.dto.PagamentoResponseDTO;
import com.example.demo.dto.PagamentoStatusUpdateDTO;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final IPagamentoRepository pagamentoRepository;
    private final IClienteRepository clienteRepository;

    public PagamentoResponseDTO registrarPagamento(PagamentoCadastroDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + dto.clienteId()));

        Pagamento pagamento = new Pagamento();
        pagamento.setCliente(cliente);
        pagamento.setValor(dto.valor());
        pagamento.setFormaPagamento(dto.formaPagamento());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setStatus("Pendente"); // Status inicial padrão

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return toResponseDTO(pagamentoSalvo);
    }

    public List<PagamentoResponseDTO> consultarPagamentosPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + clienteId);
        }
        return pagamentoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO atualizarStatus(Long id, PagamentoStatusUpdateDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado com o ID: " + id));

        pagamento.setStatus(dto.status());
        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);
        return toResponseDTO(pagamentoAtualizado);
    }

    private PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getCliente().getCliente_id(),
                pagamento.getCliente().getNome(),
                pagamento.getValor(),
                pagamento.getFormaPagamento(),
                pagamento.getDataPagamento(),
                pagamento.getStatus()
        );
    }
}