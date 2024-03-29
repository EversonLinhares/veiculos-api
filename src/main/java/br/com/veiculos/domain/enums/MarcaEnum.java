package br.com.veiculos.domain.enums;

import br.com.veiculos.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MarcaEnum {

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

    public static MarcaEnum findByNome(String nome) {
        for (MarcaEnum marcaEnum : values()) {
            if (marcaEnum.nome.equals(nome)) {
                return marcaEnum;
            }
        }
        throw new NegocioException("Não foi encontrado Marca com o nome informado: " + nome);
    }


}
