package com.livraria.projetolivraria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraria.projetolivraria.entities.LivroEntity;
import com.livraria.projetolivraria.service.LivroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroRestController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroEntity>> listar(@RequestParam(required = false) String busca) {
        // Se 'busca' for nulo, o serviço já sabe lidar e retornará todos os livros.
        return ResponseEntity.ok(livroService.listarTodos(busca));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LivroEntity> buscar(@PathVariable String isbn) {
        return livroService.buscarPorId(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroEntity> salvar(@RequestBody LivroEntity livro) {
        livroService.incluir(livro); 
        return ResponseEntity.status(201).body(livro);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<LivroEntity> atualizar(@PathVariable String isbn, @RequestBody LivroEntity dados) {
        Optional<LivroEntity> opt = livroService.buscarPorId(isbn);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        LivroEntity existente = opt.get();
        existente.setNome(dados.getNome());
        existente.setAutor(dados.getAutor());
        existente.setCategoria(dados.getCategoria());
        existente.setQuantidadeEstoque(dados.getQuantidadeEstoque());
        existente.setPreco(dados.getPreco());
        existente.setFornecedor(dados.getFornecedor());
        existente.setCadastradoPor(dados.getCadastradoPor());

        livroService.incluir(existente);
        return ResponseEntity.ok(existente);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> excluir(@PathVariable String isbn) {
        livroService.excluir(isbn);
        return ResponseEntity.noContent().build();
    }
}