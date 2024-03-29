package br.com.veiculos.domain.model;

import br.com.veiculos.domain.enums.MarcaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Boolean vendido;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime updated;


}


