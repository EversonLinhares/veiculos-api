package br.com.veiculos.domain.model;

import br.com.veiculos.domain.enums.MarcaEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class BaseEntity {

    @Column(nullable = false)
    private String veiculo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MarcaEnum marca;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private Boolean vendido = Boolean.FALSE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @Column
    private LocalDateTime updated;

}


