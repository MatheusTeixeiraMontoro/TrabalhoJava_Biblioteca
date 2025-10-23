package com.example.demo.controller;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@AllArgsConstructor
public class ClienteController {

    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        clienteService.salvarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO);
    }

    @GetMapping
    public List<ClienteDTO> consultarCliente(){
        return clienteService.consultaCliente();
    }

    @GetMapping("/{id}")
    public ClienteDTO consultarClientePorId(@PathVariable Long id){
        return clienteService.consultaClinteById(id);
    }
}
