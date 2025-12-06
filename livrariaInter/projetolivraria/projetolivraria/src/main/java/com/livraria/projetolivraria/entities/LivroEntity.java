package com.livraria.projetolivraria.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livros")
public class LivroEntity {
    @Id
    @Column(length = 32)
    private String isbnLivro;

    @Column(nullable = false, length = 255)
    @NonNull
    private String nome;

    @Column(length = 255)
    @NonNull
    private String autor;

    @Column(length = 100)
    @NonNull
    private String categoria;

    @NonNull
    private Integer quantidadeEstoque;

    @Column(precision = 12, scale = 2)
    @NonNull
    private BigDecimal preco;
    
    @ManyToOne
    @JoinColumn(name = "fk_fornecedor")
    @NonNull
    private FornecedorEntity fornecedor;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    @NonNull
    private FuncionarioEntity cadastradoPor;
}