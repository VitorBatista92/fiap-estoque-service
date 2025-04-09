package br.com.postechfiap.fiap_estoque_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = "estoque")
@Entity
public class EstoqueEntity extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "estoque_id_seq",allocationSize = 1)
    private Long id;

    @Setter
    @NotBlank(message = "O nome do produto é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @Setter
    @Column(nullable = false)
    private String sku;

    @Setter
    @Column(nullable = false)
    private Long quantidade;
}
