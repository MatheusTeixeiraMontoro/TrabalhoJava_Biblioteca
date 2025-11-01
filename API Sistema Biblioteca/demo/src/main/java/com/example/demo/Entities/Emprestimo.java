package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emprestimo_id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDateTime dataEmprestimo;

    // CAMPO QUE ESTAVA FALTANDO - AGORA CORRIGIDO
    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDateTime dataDevolucaoPrevista;

    @Column(name = "data_devolucao")
    private LocalDateTime dataDevolucao; // Data da devolução real

    @Column
    private boolean status; // true = Ativo, false = Devolvido

}