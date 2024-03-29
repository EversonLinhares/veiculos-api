package br.com.veiculos.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum VeiculoEnum {

    VOLKSWAGEN("Volkswagen"),
    CHEVROLET("Chevrolet"),
    FORD("Ford"),
    TOYOTA("Toyota"),
    HONDA("Honda"),
    HYUNDAI("Hyundai"),
    FIAT("Fiat"),
    RENAULT("Renault"),
    CITROEN("Citroën"),
    NISSAN("Nissan"),
    PEUGEOT("Peugeot"),
    KIA("Kia"),
    JEEP("Jeep"),
    MITSUBISHI("Mitsubishi"),
    LAND_ROVER("Land Rover"),
    BMW("BMW"),
    MERCEDES_BENZ("Mercedes-Benz"),
    PORSCHE("Porsche"),
    AUDI("Audi"),
    VOLVO("Volvo");

    private String nome;

    public static VeiculoEnum findByNome(String nome) {
        for (VeiculoEnum veiculoEnum : values()) {
            if (veiculoEnum.nome.equals(nome)) {
                return veiculoEnum;
            }
        }
        throw new RuntimeException("Não foi encontrado Sexo com o id informado.");
    }


}
