package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.PagamentoEntity;
import com.livraria.projetolivraria.service.PagamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoRestController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoEntity>> listar() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoEntity> buscar(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagamentoEntity> salvar(@RequestBody PagamentoEntity p) {
        p.setPedido(p.getPedido()); 

        PagamentoEntity salvo = pagamentoService.salvar(p);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoEntity> atualizar(
            @PathVariable Integer id,
            @RequestBody PagamentoEntity dados) {

        return pagamentoService.buscarPorId(id).map(existente -> {

            // Atualiza apenas campos que realmente existem na entidade
            existente.setFormaPagamento(dados.getFormaPagamento());
            existente.setDataPagamento(dados.getDataPagamento());
            existente.setValor(dados.getValor()); 
            existente.setStatus(dados.getStatus()); 

            PagamentoEntity salvo = pagamentoService.salvar(existente);
            return ResponseEntity.ok(salvo);

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
