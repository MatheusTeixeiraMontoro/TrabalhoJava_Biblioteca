package com.example.demo.service;

import com.example.demo.Entities.Emprestimo;
import com.example.demo.Entities.Multa;
import com.example.demo.dto.MultaResponseDTO;
import com.example.demo.mapper.MultaMapper;
import com.example.demo.repository.IEmprestimoRepository;
import com.example.demo.repository.IMultaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MultaService {

    private final IMultaRepository multaRepository;
    private final IEmprestimoRepository emprestimoRepository;
    private final MultaMapper multaMapper;
    private static final BigDecimal VALOR_MULTA_POR_DIA = new BigDecimal("1.50");

    //Verifica todos os empréstimos atrasados e gera multas se ainda não existirem, ou atualiza o valor se já existirem
    public List<MultaResponseDTO> verificarEGerarMultas() {

        // Busca todos os empréstimos que estão atrasados
        List<Emprestimo> emprestimosAtrasados = emprestimoRepository.findEmprestimosAtrasados(LocalDateTime.now());

        for (Emprestimo emprestimo : emprestimosAtrasados) {

            // Para cada empréstimo atrasado, verifica se uma multa já existe
            multaRepository.findByEmprestimoId(emprestimo.getEmprestimo_id()).ifPresentOrElse(
                    multaExistente -> {

                        // Se a multa já existe e está pendente, apenas recalcula o valor
                        if ("Pendente".equals(multaExistente.getStatus())) {
                            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), LocalDateTime.now());

                            // Garante que a multa seja de no mínimo 1 dia
                            multaExistente.setValor(VALOR_MULTA_POR_DIA.multiply(new BigDecimal(Math.max(diasAtraso, 1))));
                            multaRepository.save(multaExistente);
                        }
                    },
                    () -> {

                        // Se a multa não existe, cria uma nova
                        long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), LocalDateTime.now());
                        Multa novaMulta = new Multa();
                        novaMulta.setEmprestimo(emprestimo);
                        novaMulta.setValor(VALOR_MULTA_POR_DIA.multiply(new BigDecimal(Math.max(diasAtraso, 1))));
                        novaMulta.setStatus("Pendente");
                        multaRepository.save(novaMulta);
                    }
            );
        }

        // Retorna a lista atualizada de todas as multas com status "Pendente"
        return multaRepository.findAll().stream()
                .filter(multa -> "Pendente".equals(multa.getStatus()))
                .map(multaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MultaResponseDTO> consultarMultasPorCliente(Long clienteId) {
        return multaRepository.findByClienteId(clienteId).stream()
                .map(multaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MultaResponseDTO pagarMulta(Long multaId) {
        Multa multa = multaRepository.findById(multaId)
                .orElseThrow(() -> new IllegalArgumentException("Multa não encontrada com o ID: " + multaId));

        if ("Paga".equals(multa.getStatus())) {
            throw new IllegalStateException("Esta multa já foi paga.");
        }

        multa.setStatus("Paga");
        multa.setDataPagamento(LocalDateTime.now());
        Multa multaPaga = multaRepository.save(multa);
        return multaMapper.toResponseDTO(multaPaga);
    }
}