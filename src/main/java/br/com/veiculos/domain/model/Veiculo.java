package br.com.veiculos.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE veiculo SET excluido = true WHERE id = ?")
@Where(clause = "excluido = false")
@Table(name = "veiculo")
public class Veiculo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String cor;

    @Column
    private Boolean excluido = Boolean.FALSE;

}
