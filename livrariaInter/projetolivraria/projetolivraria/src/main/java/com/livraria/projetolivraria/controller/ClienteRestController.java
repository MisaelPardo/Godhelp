package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livraria.projetolivraria.entities.ClienteEntity;
import com.livraria.projetolivraria.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteRestController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listar(@RequestParam(required = false) String busca) {
        return ResponseEntity.ok(clienteService.listarTodos(busca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> buscar(@PathVariable Integer id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> salvar(@RequestBody ClienteEntity cliente) {
        return ResponseEntity.status(201).body(clienteService.incluir(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> atualizar(
        @PathVariable Integer id,
        @RequestBody ClienteEntity clienteAtualizado) {

    return clienteService.buscarPorId(id)
            .map(clienteExistente -> {
                clienteExistente.setNome(clienteAtualizado.getNome());
                clienteExistente.setEmail(clienteAtualizado.getEmail());
                clienteExistente.setTelefone(clienteAtualizado.getTelefone());
                clienteExistente.setDdd(clienteAtualizado.getDdd());
                clienteExistente.setCriadoPor(clienteAtualizado.getCriadoPor());

                ClienteEntity atualizado = clienteService.incluir(clienteExistente);
                return ResponseEntity.ok(atualizado);
            })
            .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

