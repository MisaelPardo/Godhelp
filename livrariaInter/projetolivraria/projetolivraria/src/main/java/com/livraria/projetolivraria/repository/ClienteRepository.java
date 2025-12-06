package com.livraria.projetolivraria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.livraria.projetolivraria.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer>{
    
    List<ClienteEntity> findByNomeContainingIgnoreCase(String nome);
}