package com.example.demo.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livro_id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private String autor;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidade;

    @NotBlank
    @Column(nullable = false)
    private String categoria;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> livrosEmprestados;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manutencao> manutencoes;
}
