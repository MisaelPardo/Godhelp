package com.livraria.projetolivraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.livraria.projetolivraria.entities.PedidoEntity;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {

    // Pesquisa por nome do cliente ou pelo ID do pedido
    // CAST(p.idPedido AS string) permite pesquisar digitando o número
    @Query("SELECT p FROM PedidoEntity p WHERE " +
           "LOWER(p.cliente.nome) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
           "CAST(p.idPedido AS string) LIKE :criterio")
    List<PedidoEntity> findByCriterio(@Param("criterio") String criterio);

}