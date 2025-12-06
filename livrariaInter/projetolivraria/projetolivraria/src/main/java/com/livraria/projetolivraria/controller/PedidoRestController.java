package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.PedidoEntity;
import com.livraria.projetolivraria.service.PedidoService;
import com.livraria.projetolivraria.service.ClienteService;
import com.livraria.projetolivraria.entities.ClienteEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoRestController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<PedidoEntity>> listar(@RequestParam(required = false) String busca) {
        return ResponseEntity.ok(pedidoService.listarTodos(busca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> buscar(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoEntity> salvar(@RequestBody PedidoEntity pedido) {
        if (pedido.getClienteId() != null) {
            ClienteEntity cliente = clienteService.buscarPorId(pedido.getClienteId()).orElse(null);
            pedido.setCliente(cliente);
        }
        PedidoEntity salvo = pedidoService.salvar(pedido);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoEntity> atualizar(@PathVariable Integer id, @RequestBody PedidoEntity dados) {
        return pedidoService.buscarPorId(id).map(existente -> {
            if (dados.getClienteId() != null) {
                ClienteEntity cliente = clienteService.buscarPorId(dados.getClienteId()).orElse(null);
                existente.setCliente(cliente);
            }
            if (dados.getDataPedido() != null) existente.setDataPedido(dados.getDataPedido());
            PedidoEntity salvo = pedidoService.salvar(existente);
            return ResponseEntity.ok(salvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}