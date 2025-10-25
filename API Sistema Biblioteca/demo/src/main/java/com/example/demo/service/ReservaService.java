package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Livro;
import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IEmprestimoRepository;
import com.example.demo.repository.ILivroRepository;
import com.example.demo.repository.IReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor // Mantido para consistência com seu exemplo
public class ReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private IEmprestimoRepository emprestimoRepository; // Necessário para a lógica

    /**
     * Cria uma reserva, seguindo a lógica de negócio:
     * 1. Só se pode reservar se o livro NÃO estiver disponível.
     * 2. O cliente não pode ter outra reserva ativa para o mesmo livro.
     */
    public ResponseEntity<String> criarReserva(ReservaDTO reservaDTO) {
        // 1. Validar se o cliente e o livro existem (estilo igual ao seu)
        Cliente cliente = clienteRepository.findById(reservaDTO.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Livro livro = livroRepository.findById(reservaDTO.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        // 2. REGRA DE NEGÓCIO: Verificar disponibilidade
        // Contamos quantos empréstimos ATIVOS (status=true) existem para este livro
        long emprestimosAtivos = emprestimoRepository.countByLivroAndStatus(livro, true);

        // Verificamos as cópias disponíveis
        long copiasDisponiveis = livro.getQuantidade() - emprestimosAtivos;

        if (copiasDisponiveis > 0) {
            // Se há cópias, não se pode reservar. Deve-se fazer um empréstimo.
            throw new IllegalArgumentException("Livro está disponível para empréstimo. Não é possível reservar.");
        }

        // 3. REGRA DE NEGÓCIO: Verificar se o cliente já tem reserva ativa
        // Usamos o método do IReservaRepository (status=true)
        boolean jaPossuiReserva = reservaRepository.existsByClienteAndLivroAndStatus(cliente, livro, true);
        if (jaPossuiReserva) {
            throw new IllegalArgumentException("Cliente já possui uma reserva ativa para este livro.");
        }

        // 4. Se todas as regras passarem, criar a reserva
        Reserva novaReserva = new Reserva();
        novaReserva.setCliente(cliente);
        novaReserva.setLivro(livro);
        novaReserva.setDataReserva(LocalDateTime.now());
        novaReserva.setStatus(true); // Define a reserva como "Ativa"

        reservaRepository.save(novaReserva);

        // 5. Retornar ResponseEntity, como no seu exemplo
        return ResponseEntity.ok("Reserva criada com sucesso!");
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada com id: " + id));
    }

    /**
     * Cancela uma reserva (muda o status para false/inativo).
     * Segue seu padrão de retornar ResponseEntity<String>.
     */
    public ResponseEntity<String> cancelarReserva(Long id) {
        // Reutiliza o método acima para buscar
        Reserva reserva = buscarPorId(id);

        if (!reserva.isStatus()) { // Se o status já for 'false'
            throw new IllegalArgumentException("Reserva já está inativa/cancelada.");
        }

        reserva.setStatus(false); // Define o status como inativo
        reservaRepository.save(reserva);

        return ResponseEntity.ok("Reserva cancelada com sucesso.");
    }
}