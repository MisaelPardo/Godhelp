package com.livraria.projetolivraria.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.livraria.projetolivraria.entities.FuncionarioEntity;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Long> {
    Optional<FuncionarioEntity> findByEmail(String email);

    List<FuncionarioEntity> findByNomeContainingIgnoreCase(String nome);
}
