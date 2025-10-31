package com.example.demo.service;

import com.example.demo.Entities.Livro;
import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.ILivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LivroService {

    private final ILivroRepository livroRepository;

    public Livro criarLivro(LivroDTO livroDTO) {

        Livro novoLivro = new Livro();
        novoLivro.setTitulo(livroDTO.titulo());
        novoLivro.setAutor(livroDTO.autor());
        novoLivro.setIsbn(livroDTO.isbn());
        novoLivro.setQuantidade(livroDTO.quantidade());
        novoLivro.setCategoria(livroDTO.categoria());

        return livroRepository.save(novoLivro);
    }

    public List<LivroDTO> listarTodosOsLivros() {

        List<Livro> livros = livroRepository.findAll();

        // Converte a lista de entidades Livro para uma lista de LivroDTO
        return livros.stream()
                .map(livro -> new LivroDTO(
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getIsbn(),
                        livro.getQuantidade(),
                        livro.getCategoria()))
                .collect(Collectors.toList());
    }

}
