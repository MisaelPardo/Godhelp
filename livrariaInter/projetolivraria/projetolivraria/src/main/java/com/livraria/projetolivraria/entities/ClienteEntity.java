package com.livraria.projetolivraria.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(nullable=false, length=150)
    private String nome;

    @Column(length=20)
    private String telefone;

    @Column(length=4)
    private String ddd;

    @Column(length=150)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagamentoEntity> pagamentos;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PedidoEntity> pedidos;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private FuncionarioEntity criadoPor;
}