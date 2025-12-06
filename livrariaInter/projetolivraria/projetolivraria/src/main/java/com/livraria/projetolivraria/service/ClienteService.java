package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.livraria.projetolivraria.entities.ClienteEntity;
import com.livraria.projetolivraria.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository; 

    
    public List<ClienteEntity> listarTodos(String busca) {
        if (busca != null && !busca.isEmpty()) {
            return clienteRepository.findByNomeContainingIgnoreCase(busca);
        }
        return clienteRepository.findAll();
    }

    public ClienteEntity incluir(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<ClienteEntity> buscarPorId(Integer id) {
        if (id == null) return Optional.empty();
        return clienteRepository.findById(id);
    }

    public void excluir(Integer id) {
        if (id != null && clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }
    }
}