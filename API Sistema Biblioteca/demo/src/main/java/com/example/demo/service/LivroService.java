package com.example.demo.service;

import com.example.demo.Entities.Livro;
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
    public Livro criarLivro(LivroDTO livroDTO) {

        Livro novoLivro = new Livro();
        novoLivro.setLivro_id(livroDTO.livro_id());
        novoLivro.setTitulo(livroDTO.titulo());
        novoLivro.setAutor(livroDTO.autor());
        novoLivro.setIsbn(livroDTO.isbn());
        novoLivro.setQuantidade(livroDTO.quantidade());
        novoLivro.setCategoria(livroDTO.categoria());

        return livroRepository.save(novoLivro);
    }

    public List<LivroDTO> listarTodosOsLivros() {
        // Busca todos os livros do banco de dados
        List<Livro> livros = livroRepository.findAll();

        // Converte a lista de entidades Livro para uma lista de LivroDTO
        return livros.stream()
                .map(livro -> new LivroDTO(
                        livro.getLivro_id(),
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getIsbn(),
                        livro.getQuantidade(),
                        livro.getCategoria()))
                .collect(Collectors.toList());
    }


    // 1. BUSCAR LIVRO POR ID (GET /api/livros/{id})
    public LivroDTO buscarLivroPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));

        return new LivroDTO(
                livro.getLivro_id(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(), // Incluído o ISBN
                livro.getQuantidade(),
                livro.getCategoria());
    }


    // 2. LISTAR LIVROS DISPONÍVEIS (GET /api/livros/disponiveis)
    public List<LivroDTO> listarLivrosDisponiveis() {
        // Regra de Negócio: Filtra livros onde a quantidade é maior que zero para empréstimo
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getQuantidade() > 0)
                .map(livro -> new LivroDTO(
                        livro.getLivro_id(),
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getIsbn(), // Incluído o ISBN
                        livro.getQuantidade(),
                        livro.getCategoria()))
                .collect(Collectors.toList());
    }


    // 3. ATUALIZAR LIVRO (PUT /api/livros/{id})
    @Transactional
    public LivroDTO atualizarLivro(Long id, LivroDTO livroDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));

        livroExistente.setLivro_id(livroDTO.livro_id());
        livroExistente.setTitulo(livroDTO.titulo());
        livroExistente.setAutor(livroDTO.autor());
        livroExistente.setIsbn(livroDTO.isbn());
        livroExistente.setQuantidade(livroDTO.quantidade());
        livroExistente.setCategoria(livroDTO.categoria());

        // Salva a entidade atualizada
        Livro livroAtualizado = livroRepository.save(livroExistente);

        // Mapeia para DTO e retorna
        return new LivroDTO(
                livroAtualizado.getLivro_id(),
                livroAtualizado.getTitulo(),
                livroAtualizado.getAutor(),
                livroAtualizado.getIsbn(), // Incluído o ISBN
                livroAtualizado.getQuantidade(),
                livroAtualizado.getCategoria());
    }


    // 4. EXCLUIR LIVRO (DELETE /api/livros/{id})
    @Transactional
    public void excluirLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }
}