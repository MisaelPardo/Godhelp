package com.livraria.projetolivraria.repository;

import com.livraria.projetolivraria.entities.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

// --- DTOs (Mantidos iguais) ---
interface LivroFaixaDTO {
    String getIsbn_livro(); 
    String getNome();
    String getAutor();
    BigDecimal getPreco();
    String getNome_fornecedor();
}

interface RelatorioVendaDTO {
    Integer getIdPedido();
    String getNomeCliente();
    Date getDataVenda();
    BigDecimal getValorTotal();
    String getStatusPagamento();
    Integer getQtdItens();
}

interface HistoricoPrecoDTO {
    String getIsbn_livro();
    BigDecimal getPreco_antigo();
    BigDecimal getPreco_novo();
    Date getData_alteracao();
}

@Repository
public interface LivrariaRequisitosRepository extends JpaRepository<LivroEntity, String> {

    // 1. FUNCTION
    @Query(value = "SELECT * FROM dbo.FN_Buscar_Livros_Por_Faixa_Preco(:min, :max)", nativeQuery = true)
    List<LivroFaixaDTO> buscarPorFaixa(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // 2. VIEW
    @Query(value = "SELECT * FROM dbo.VW_Relatorio_Vendas_Detalhado", nativeQuery = true)
    List<RelatorioVendaDTO> buscarRelatorioVendas();

    // 3. PROCEDURE (ATUALIZADA PARA O SEU SQL)
    // Nome alterado para sp_RealizarPedidoLivro e adicionado FormaPagamento
    @Transactional 
    @Procedure(procedureName = "dbo.sp_RealizarPedidoLivro")
    void efetuarCompraSegura(
        @Param("IdCliente") Integer idCliente, 
        @Param("IsbnLivro") String isbnLivro, 
        @Param("Quantidade") Integer quantidade,
        @Param("FormaPagamento") String formaPagamento
    );

    // 4. TRIGGER / HISTÓRICO
    @Query(value = "SELECT TOP 20 * FROM dbo.historico_precos ORDER BY data_alteracao DESC", nativeQuery = true)
    List<HistoricoPrecoDTO> buscarHistoricoPrecos();
}