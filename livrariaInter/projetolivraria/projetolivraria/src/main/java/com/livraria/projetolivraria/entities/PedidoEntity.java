package com.livraria.projetolivraria.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private ClienteEntity cliente;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;

    @Transient
    private Integer clienteId;

    @Transient
    private Integer totalItens; // SOMA DAS QUANTIDADES DOS ITENS

    @JsonIgnore
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LivroPedidoEntity> itens;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PagamentoEntity> pagamentos;
}