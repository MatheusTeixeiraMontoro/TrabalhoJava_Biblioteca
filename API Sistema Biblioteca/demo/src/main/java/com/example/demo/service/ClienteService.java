package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.repository.IClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private IClienteRepository clienteRepository;


    public ResponseEntity salvarCliente(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();

        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setEndereco(clienteDTO.endereco());

        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }

    public List<ClienteDTO> consultaCliente(){

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getTelefone(),
                        cliente.getEndereco()))
                .collect(Collectors.toList());
    }

    public ClienteDTO consultaClinteById(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado"));

        return new ClienteDTO(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco()  

        );
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID: " + id));

        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setEndereco(clienteDTO.endereco());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return new ClienteDTO(
                clienteAtualizado.getNome(),
                clienteAtualizado.getEmail(),
                clienteAtualizado.getTelefone(),
                clienteAtualizado.getEndereco()
        );
    }

    public void deletarCliente(Long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID: " + id));

        clienteRepository.deleteById(id);
    }


}
