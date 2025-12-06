package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livraria.projetolivraria.entities.LivroPedidoEntity;
import com.livraria.projetolivraria.repository.LivroPedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroPedidoService {

    private final LivroPedidoRepository livroPedidoRepository;

    public List<LivroPedidoEntity> listarTodos() {
        return livroPedidoRepository.findAll();
    }

    public Optional<LivroPedidoEntity> buscarPorId(Integer id) {
        // 2. Proteção: se ID for nulo, retorna vazio
        if (id == null) return Optional.empty();
        return livroPedidoRepository.findById(id);
    }

    public LivroPedidoEntity incluir(LivroPedidoEntity livroPedido) {
        return livroPedidoRepository.save(livroPedido);
    }

    // 3. Atualiza os campos em vez de substituir tudo
    public LivroPedidoEntity editar(Integer id, LivroPedidoEntity novosDados) {
        if (id == null) return null;

        return livroPedidoRepository.findById(id)
            .map(itemExistente -> {
                // Atualizar a quantidade
                if (novosDados.getQuantidade() != null) {
                    itemExistente.setQuantidade(novosDados.getQuantidade());
                }
                
                // Se mandaram um novo Livro ou Pedido no JSON, atualizamos os vínculos
                if (novosDados.getLivro() != null) {
                    itemExistente.setLivro(novosDados.getLivro());
                }
                if (novosDados.getPedido() != null) {
                    itemExistente.setPedido(novosDados.getPedido());
                }

                return livroPedidoRepository.save(itemExistente);
            })
            .orElse(null);
    }

    public void excluir(Integer id) {
        // 4. Verifica se existe antes de tentar apagar
        if (id != null && livroPedidoRepository.existsById(id)) {
            livroPedidoRepository.deleteById(id);
        }
    }
}