package com.livraria.projetolivraria.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.livraria.projetolivraria.entities.LivroEntity;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, String> {

    // Pesquisa unificada por Nome, Autor, Categoria ou ISBN (usando o LIKE e ignorando case)
    @Query("SELECT l FROM LivroEntity l WHERE " +
           "LOWER(l.nome) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
           "LOWER(l.autor) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
           "LOWER(l.categoria) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
           "LOWER(l.isbnLivro) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<LivroEntity> findByCriterio(@Param("criterio") String criterio);

    // Método original usando stored procedure (se ainda quiser manter)
    @Transactional
    @Modifying
    @Query(value = "EXEC sp_AtualizarEstoque :idLivro, :qtd", nativeQuery = true)
    void baixarEstoque(@Param("idLivro") String idLivro, @Param("qtd") int qtd);

    // Método para decrementar estoque diretamente via JPQL, com proteção contra estoque negativo
    @Modifying
    @Transactional
    @Query("UPDATE LivroEntity l SET l.quantidadeEstoque = l.quantidadeEstoque - :quant " +
           "WHERE l.isbnLivro = :id AND l.quantidadeEstoque >= :quant")
    int decrementarEstoque(@Param("id") String id, @Param("quant") Integer quant);

    // Consulta quantidade disponível
    @Query("SELECT l.quantidadeEstoque FROM LivroEntity l WHERE l.isbnLivro = :id")
    Integer findQuantidadeEstoque(@Param("id") String id);

    // Busca por ISBN (opcional, se precisar)
    Optional<LivroEntity> findById(String id);
}