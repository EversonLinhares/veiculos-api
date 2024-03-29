package br.com.veiculos.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VeiculoRequestDTO {

    @NotEmpty(message = "O campo veiculo deve ser preenchido.")
    private String veiculo;
    @NotEmpty(message = "O campo marca deve ser preenchido.")
    private String marca;
    @Min(value = 0, message = "O ano não pode ser negativo.")
    @Max(value = 9999, message = "O ano deve ter no máximo 4 dígitos.")
    @NotNull(message = "O campo ano deve ser preenchido.")
    private Integer ano;
    @NotEmpty(message = "O campo descricao deve ser preenchido.")
    private String descricao;
    private Boolean vendido = Boolean.FALSE;
    @NotEmpty(message = "O campo cor deve ser preenchido.")
    private String cor;

}
