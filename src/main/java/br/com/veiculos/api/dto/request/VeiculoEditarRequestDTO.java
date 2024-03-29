package br.com.veiculos.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VeiculoEditarRequestDTO {

    @NotNull(message = "O campo vendido deve ser preenchido.")
    private Boolean vendido;
}
