package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.livraria.projetolivraria.entities.FornecedorEntity;
import com.livraria.projetolivraria.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public List<FornecedorEntity> listarTodos(String busca) {
        if (busca != null && !busca.isEmpty()) {
            return fornecedorRepository.findByNomeContainingIgnoreCase(busca);
        }
        return fornecedorRepository.findAll();
    }

    public FornecedorEntity salvar(FornecedorEntity fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<FornecedorEntity> buscarPorId(Integer id) {
        if (id == null) return Optional.empty();
        return fornecedorRepository.findById(id);
    }

    public void deletar(Integer id) {
        if (id != null && fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
        }
    }
}