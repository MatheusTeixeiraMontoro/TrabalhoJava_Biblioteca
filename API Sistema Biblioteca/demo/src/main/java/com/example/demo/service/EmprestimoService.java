package com.example.demo.service;

import com.example.demo.Entities.Emprestimo;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.dto.EmprestimoDTO;
import com.example.demo.repository.IEmprestimoRepository;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.ILivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmprestimoService {

    @Autowired
    private IEmprestimoRepository emprestimoRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ILivroRepository livroRepository;

    public ResponseEntity<String> criarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Cliente cliente = clienteRepository.findById(emprestimoDTO.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Livro livro = livroRepository.findById(emprestimoDTO.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setCliente(cliente);
        emprestimo.setLivro(livro);

        emprestimo.setDataEmprestimo(LocalDateTime.now());

        emprestimo.setStatus(true);
        emprestimo.setDataDevolucao(emprestimoDTO.dataDevolucao());

        emprestimoRepository.save(emprestimo);
        return ResponseEntity.ok("Empréstimo criado com sucesso!");
    }

}
