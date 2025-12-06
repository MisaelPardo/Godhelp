package com.livraria.projetolivraria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.FornecedorEntity;
import com.livraria.projetolivraria.service.FornecedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorRestController {

    private final FornecedorService fornecedorService;

    // LISTAR 
    @GetMapping
    public ResponseEntity<List<FornecedorEntity>> listarTodos() {
        List<FornecedorEntity> fornecedores = fornecedorService.listarTodos(null);
        return ResponseEntity.ok(fornecedores);
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorEntity> buscarPorId(@PathVariable Integer id) {
        Optional<FornecedorEntity> fornecedorOpt = fornecedorService.buscarPorId(id);
        return fornecedorOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // SALVAR 
    @PostMapping
    public ResponseEntity<FornecedorEntity> salvar(@RequestBody FornecedorEntity fornecedor) {
        FornecedorEntity salvo = fornecedorService.salvar(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorEntity> atualizar(@PathVariable Integer id,
    @RequestBody FornecedorEntity fornecedor) {
        Optional<FornecedorEntity> fornecedorExistente = fornecedorService.buscarPorId(id);
        if (fornecedorExistente.isPresent()) {
            fornecedor.setIdFornecedor(id);
            FornecedorEntity atualizado = fornecedorService.salvar(fornecedor);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Optional<FornecedorEntity> fornecedorOpt = fornecedorService.buscarPorId(id);
        if (fornecedorOpt.isPresent()) {
            fornecedorService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}