package com.livraria.projetolivraria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.livraria.projetolivraria.entities.FornecedorEntity;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Integer> {

    List<FornecedorEntity> findByNomeContainingIgnoreCase(String nome);
}