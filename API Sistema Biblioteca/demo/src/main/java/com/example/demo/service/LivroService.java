package com.example.demo.service;

import com.example.demo.Entities.Livro;
import com.example.demo.dto.LivroCadastroDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.ILivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LivroService {

    private final ILivroRepository livroRepository;

    @Transactional
    public LivroDTO criarLivro(LivroCadastroDTO livroCadastroDTO) {
        Livro novoLivro = new Livro();

        novoLivro.setTitulo(livroCadastroDTO.titulo());
        novoLivro.setAutor(livroCadastroDTO.autor());
        novoLivro.setIsbn(livroCadastroDTO.isbn());
        novoLivro.setQuantidade(livroCadastroDTO.quantidade());
        novoLivro.setCategoria(livroCadastroDTO.categoria());

        Livro livroSalvo = livroRepository.save(novoLivro);

        return toDTO(livroSalvo);
    }

    public List<LivroDTO> listarTodosOsLivros() {
        return livroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO buscarLivroPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));
        return toDTO(livro);
    }

    public List<LivroDTO> listarLivrosDisponiveis() {
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getQuantidade() > 0)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LivroDTO atualizarLivro(Long id, LivroDTO livroDTO) {

        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));

        livroExistente.setTitulo(livroDTO.titulo());
        livroExistente.setAutor(livroDTO.autor());
        livroExistente.setIsbn(livroDTO.isbn());
        livroExistente.setQuantidade(livroDTO.quantidade());
        livroExistente.setCategoria(livroDTO.categoria());

        Livro livroAtualizado = livroRepository.save(livroExistente);

        return toDTO(livroAtualizado);
    }

    @Transactional
    public void excluirLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getLivro_id(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getQuantidade(),
                livro.getCategoria()
        );
    }
}