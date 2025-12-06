package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.livraria.projetolivraria.entities.LivroEntity;
import com.livraria.projetolivraria.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public List<LivroEntity> listarTodos(String criterio) {
        if (criterio != null && !criterio.isEmpty()) {
            return livroRepository.findByCriterio(criterio);
        }
        return livroRepository.findAll();
    }

    public void incluir(@NonNull LivroEntity livro) {
        livroRepository.save(livro);
    }

    public Optional<LivroEntity> buscarPorId(@NonNull String isbn) {
        return livroRepository.findById(isbn);
    }

    public void excluir(@NonNull String isbn) {
        livroRepository.deleteById(isbn);
    }

    
    /* 
     -- Verifica se há estoque suficiente,
     -- Retorna true se houver estoque, false caso contrário.
     */
    public boolean verificarDisponibilidade(String isbn, int quantidadeSolicitada) {
        LivroEntity livro = livroRepository.findById(isbn).orElse(null);
        return livro != null && livro.getQuantidadeEstoque() >= quantidadeSolicitada;
    }

    /**
     * Realiza a baixa (subtração) do estoque no banco de dados.
     */
    public void baixarEstoque(String isbn, int quantidadeBaixa) {
        LivroEntity livro = livroRepository.findById(isbn).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        int novaQuantidade = livro.getQuantidadeEstoque() - quantidadeBaixa;
        
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Estoque insuficiente para realizar a baixa.");
        }

        livro.setQuantidadeEstoque(novaQuantidade);
        livroRepository.save(livro);
    }
}