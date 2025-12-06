package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livraria.projetolivraria.entities.PagamentoEntity;
import com.livraria.projetolivraria.entities.PedidoEntity;
import com.livraria.projetolivraria.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoEntity> listarTodos(String busca) {
        if (busca != null && !busca.isBlank()) {
            return pedidoRepository.findByCriterio(busca);
        }
        return pedidoRepository.findAll();
    }

    public Optional<PedidoEntity> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public PedidoEntity salvar(PedidoEntity pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deletar(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public void atualizarStatus(Integer idPedido, String novoStatus) {
        Optional<PedidoEntity> op = pedidoRepository.findById(idPedido);
        if (op.isPresent()) {
            PedidoEntity pedido = op.get();
            if (pedido.getPagamentos() != null && !pedido.getPagamentos().isEmpty()) {
                // Atualiza o primeiro pagamento (assumindo 1 pag por pedido)
                PagamentoEntity pag = pedido.getPagamentos().get(0);
                pag.setStatus(novoStatus);
                pedidoRepository.save(pedido);
            }
        }
    }
}