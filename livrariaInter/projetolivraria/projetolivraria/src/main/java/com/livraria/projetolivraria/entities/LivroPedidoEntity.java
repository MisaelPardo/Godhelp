package com.livraria.projetolivraria.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro_pedido")
public class LivroPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "fk_livro")
    private LivroEntity livro;

    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private PedidoEntity pedido;
}
