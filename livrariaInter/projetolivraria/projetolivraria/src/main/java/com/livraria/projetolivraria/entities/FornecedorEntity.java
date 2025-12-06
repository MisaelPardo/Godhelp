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
@Table(name = "fornecedores")
public class FornecedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFornecedor;

    @Column(nullable=false, length=200)
    private String nome;

    @Column(length=300)
    private String endereco;

    @Column(length=20)
    private String cnpj;

    @Column(length=20)
    private String telefone;

    @Column(length=4)
    private String ddd;

    @JsonIgnore
    @OneToMany(mappedBy = "fornecedor")
    private List<LivroEntity> livros;
}
