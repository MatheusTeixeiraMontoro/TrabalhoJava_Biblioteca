package com.example.demo.service;

import com.example.demo.Entities.Livro;
import com.example.demo.Entities.Manutencao;
import com.example.demo.dto.ManutencaoCadastroDTO;
import com.example.demo.dto.ManutencaoResponseDTO;
import com.example.demo.repository.ILivroRepository;
import com.example.demo.repository.IManutencaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManutencaoService {

    private final IManutencaoRepository manutencaoRepository;
    private final ILivroRepository livroRepository;

    public ManutencaoResponseDTO registrarManutencao(ManutencaoCadastroDTO dto) {
        Livro livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + dto.livroId()));

        Manutencao manutencao = new Manutencao();
        manutencao.setLivro(livro);
        manutencao.setDescricao(dto.descricao());
        manutencao.setDataInicio(dto.dataInicio());
        manutencao.setStatus("Em Andamento");

        Manutencao manutencaoSalva = manutencaoRepository.save(manutencao);
        return toResponseDTO(manutencaoSalva);
    }

    public List<ManutencaoResponseDTO> listarEmAndamento() {
        return manutencaoRepository.findByStatus("Em Andamento").stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ManutencaoResponseDTO concluirManutencao(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada com o ID: " + id));

        manutencao.setStatus("Concluída");
        manutencao.setDataTermino(LocalDateTime.now()); // Define a data de término no momento da conclusão

        Manutencao manutencaoAtualizada = manutencaoRepository.save(manutencao);
        return toResponseDTO(manutencaoAtualizada);
    }

    public List<ManutencaoResponseDTO> consultarHistoricoPorLivro(Long livroId) {
        if (!livroRepository.existsById(livroId)) {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + livroId);
        }
        return manutencaoRepository.findByLivroId(livroId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ManutencaoResponseDTO toResponseDTO(Manutencao manutencao) {
        return new ManutencaoResponseDTO(
                manutencao.getId(),
                manutencao.getLivro().getLivro_id(),
                manutencao.getLivro().getTitulo(),
                manutencao.getDescricao(),
                manutencao.getDataInicio(),
                manutencao.getDataTermino(),
                manutencao.getStatus()
        );
    }
}
