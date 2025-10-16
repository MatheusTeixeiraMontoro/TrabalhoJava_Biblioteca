package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.repository.IClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
