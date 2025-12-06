package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livraria.projetolivraria.entities.LivroPedidoEntity;
import com.livraria.projetolivraria.service.LivroPedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/livroPedido")
public class LivroPedidoController {
    
    private final LivroPedidoService livroPedidoService;

    @GetMapping
    public ResponseEntity<List<LivroPedidoEntity>> listarTodos() {
        List<LivroPedidoEntity> lista = livroPedidoService.listarTodos();
        return ResponseEntity.ok().body(lista);
    }

    @PostMapping
    public ResponseEntity<LivroPedidoEntity> incluir(@RequestBody LivroPedidoEntity livroPedido) {
        LivroPedidoEntity novo = livroPedidoService.incluir(livroPedido);
        if (novo != null) {
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroPedidoEntity> editar(@PathVariable Integer id, @RequestBody LivroPedidoEntity livroPedido) {
        LivroPedidoEntity atualizado = livroPedidoService.editar(id, livroPedido);
        if (atualizado != null) {
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        livroPedidoService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}