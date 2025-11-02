package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Emprestimo;
import com.example.demo.Entities.Livro;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EmprestimoDTO;
import com.example.demo.dto.EmprestimoResponseDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IEmprestimoRepository;
import com.example.demo.repository.ILivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmprestimoService {

    private final IEmprestimoRepository emprestimoRepository;
    private final IClienteRepository clienteRepository;
    private final ILivroRepository livroRepository;

    @Transactional
    public EmprestimoResponseDTO criarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Cliente cliente = clienteRepository.findById(emprestimoDTO.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + emprestimoDTO.clienteId()));
        Livro livro = livroRepository.findById(emprestimoDTO.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + emprestimoDTO.livroId()));

        if (livro.getQuantidade() <= 0) {
            throw new IllegalStateException("Livro indisponível para empréstimo.");
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCliente(cliente);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setStatus(true);

        emprestimo.setDataDevolucaoPrevista(emprestimoDTO.dataDevolucaoPrevista());

        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return toResponseDTO(emprestimoSalvo);
    }

    private EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo) {
        Cliente cliente = emprestimo.getCliente();
        Livro livro = emprestimo.getLivro();
        return new EmprestimoResponseDTO(
                emprestimo.getEmprestimo_id(),
                emprestimo.isStatus(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                new ClienteDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()),
                new LivroDTO(livro.getLivro_id(), livro.getTitulo(), livro.getAutor(), livro.getIsbn(), livro.getQuantidade(), livro.getCategoria())
        );
    }

    public List<EmprestimoResponseDTO> listarTodosEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoResponseDTO atualizarStatus(Long id, Boolean newStatus) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado com ID: " + id));
        emprestimo.setStatus(newStatus);
        return toResponseDTO(emprestimoRepository.save(emprestimo));
    }

    public List<EmprestimoResponseDTO> consultarEmprestimosPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId);
        }
        return emprestimoRepository.findEmprestimosByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoResponseDTO> consultarEmprestimosAtrasados() {
        return emprestimoRepository.findEmprestimosAtrasados(LocalDateTime.now()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmprestimoResponseDTO registrarDevolucao(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado com ID: " + id));

        if (!emprestimo.isStatus()) {
            throw new IllegalStateException("O livro já foi devolvido.");
        }

        emprestimo.setStatus(false);
        emprestimo.setDataDevolucao(LocalDateTime.now());

        Livro livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        livroRepository.save(livro);

        return toResponseDTO(emprestimoRepository.save(emprestimo));
    }
}