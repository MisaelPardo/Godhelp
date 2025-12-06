package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.LivroPedidoEntity;
import com.livraria.projetolivraria.service.LivroPedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/livroPedido")
@RequiredArgsConstructor
public class LivroPedidoRestController {

    private final LivroPedidoService livroPedidoService;

    @GetMapping
    public ResponseEntity<List<LivroPedidoEntity>> listar() {
        return ResponseEntity.ok(livroPedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroPedidoEntity> buscar(@PathVariable Integer id) {
        return livroPedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroPedidoEntity> salvar(@RequestBody LivroPedidoEntity lp) {
        LivroPedidoEntity criado = livroPedidoService.incluir(lp);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroPedidoEntity> atualizar(@PathVariable Integer id, @RequestBody LivroPedidoEntity dados) {
        LivroPedidoEntity atualizado = livroPedidoService.editar(id, dados);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        livroPedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
