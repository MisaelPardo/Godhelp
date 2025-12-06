package com.livraria.projetolivraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.livraria.projetolivraria.entities.LivroPedidoEntity;

@Repository
public interface LivroPedidoRepository extends JpaRepository<LivroPedidoEntity, Integer>{

}
