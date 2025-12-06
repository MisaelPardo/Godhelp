package com.livraria.projetolivraria.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamentos")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagamento;

    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private PedidoEntity pedido;

    // <-- ADICIONADO: relação com Cliente (existia mapeamento inverso)
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private ClienteEntity cliente;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    private LocalDate dataPagamento;

    private LocalDate dataVencimento;

    @Column(length = 100)
    private String formaPagamento;

    @Column(length = 50)
    private String status; // ex: PENDENTE, PAGO, CANCELADO
}
