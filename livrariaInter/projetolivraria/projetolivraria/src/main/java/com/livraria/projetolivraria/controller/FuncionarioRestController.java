package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioRestController {

    private final FuncionarioService funcionarioService;
    private static final Long ADMIN_ID = 1L;

    @GetMapping
    public ResponseEntity<List<FuncionarioEntity>> listar(@RequestParam(required = false) String busca) {
        return ResponseEntity.ok(funcionarioService.listarTodos(busca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioEntity> buscar(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody FuncionarioEntity funcionario) {
        try {
            FuncionarioEntity criado = funcionarioService.salvarOuAtualizar(funcionario, null);
            return ResponseEntity.status(201).body(criado);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody FuncionarioEntity novosDados) {

        if (ADMIN_ID.equals(id)) {
            return ResponseEntity.status(403).body("O admin (ID 1) não pode ser alterado.");
        }

        try {
            novosDados.setId(id);
            FuncionarioEntity atualizado = funcionarioService.salvarOuAtualizar(novosDados, null);
            return ResponseEntity.ok(atualizado);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        if (ADMIN_ID.equals(id)) {
            return ResponseEntity.status(403).body("O admin (ID 1) não pode ser excluído.");
        }

        try {
            funcionarioService.deletar(id, null);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }
}