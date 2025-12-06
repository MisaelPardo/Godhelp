package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livraria.projetolivraria.entities.PagamentoEntity;
import com.livraria.projetolivraria.repository.PagamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public List<PagamentoEntity> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public PagamentoEntity salvar(PagamentoEntity pagamento) {
        return pagamentoRepository.save(pagamento);
    }
    
    // 3. Método útil adicionado com proteção
    public Optional<PagamentoEntity> buscarPorId(Integer id) {
        if (id == null) return Optional.empty();
        return pagamentoRepository.findById(id);
    }

    public void deletar(Integer id) {
        // 4. Só tenta deletar se o ID não for nulo e o registro existir
        if (id != null && pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
        }
    }
}