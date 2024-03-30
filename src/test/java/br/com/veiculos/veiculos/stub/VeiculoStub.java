package br.com.veiculos.veiculos.stub;

import br.com.veiculos.api.dto.request.VeiculoRequestDTO;
import br.com.veiculos.api.dto.response.VeiculoResponseDTO;
import br.com.veiculos.domain.enums.MarcaEnum;
import br.com.veiculos.domain.model.Veiculo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VeiculoStub {

    public static VeiculoRequestDTO criarVeiculoRequestDTO() {
        return VeiculoRequestDTO.builder()
                .veiculo("Modelo")
                .marca("Ford")
                .ano(2020)
                .descricao("Descrição")
                .vendido(false)
                .build();
    }

    public static Veiculo criarVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setVeiculo("Modelo");
        veiculo.setMarca(MarcaEnum.FORD);
        veiculo.setAno(2020);
        veiculo.setDescricao("Descrição veiculo");
        veiculo.setVendido(false);
        veiculo.setCreated(LocalDateTime.now());
        return veiculo;
    }

    public static Veiculo getVeiculoFiltro() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setCor("Preto");
        veiculo.setVeiculo("Modelo");
        veiculo.setMarca(MarcaEnum.FORD);
        veiculo.setAno(2020);
        veiculo.setDescricao("Descrição veiculo");
        veiculo.setVendido(false);
        veiculo.setCreated(LocalDateTime.now());
        return veiculo;
    }

    public static VeiculoRequestDTO getVeiculoRequestDTOEditar(){
        VeiculoRequestDTO veiculoRequestDTO = new VeiculoRequestDTO();
        veiculoRequestDTO.setMarca("Ford");
        veiculoRequestDTO.setAno(2020);
        veiculoRequestDTO.setVeiculo("Modelo");
        veiculoRequestDTO.setDescricao("Descrição");
        return veiculoRequestDTO;
    }

    public static Veiculo getVeiculoEditar() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setVeiculo("Modelo");
        veiculo.setMarca(MarcaEnum.CHEVROLET);
        veiculo.setAno(2015);
        veiculo.setDescricao("Descrição veiculo");
        veiculo.setVendido(false);
        veiculo.setCreated(LocalDateTime.now());
        return veiculo;
    }

    public static VeiculoResponseDTO getVeiculoResponse() {
        VeiculoResponseDTO veiculo = new VeiculoResponseDTO();
        veiculo.setId(1L);
        veiculo.setVeiculo("Modelo");
        veiculo.setMarca("Ford");
        veiculo.setAno(2020);
        veiculo.setDescricao("Descrição");
        veiculo.setVendido(false);
        veiculo.setCor("Preto");
        veiculo.setCreated(LocalDateTime.now());
        return veiculo;
    }

    public static List<Veiculo> getVeiculosUltimaSemana() {
        List<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(criarVeiculo());
        return veiculos;
    }


    public static List<VeiculoResponseDTO> getVeiculoDTOUltimaSemana() {
        List<VeiculoResponseDTO> veiculos = new ArrayList<>();
        veiculos.add(getVeiculoResponse());
        return veiculos;
    }

    public static VeiculoResponseDTO getVeiculoResponseVendido() {
        VeiculoResponseDTO veiculo = new VeiculoResponseDTO();
        veiculo.setId(1L);
        veiculo.setVeiculo("Modelo");
        veiculo.setMarca("Ford");
        veiculo.setAno(2020);
        veiculo.setDescricao("Descrição");
        veiculo.setVendido(true);
        veiculo.setCor("Preto");
        veiculo.setCreated(LocalDateTime.now());
        veiculo.setUpdated(LocalDateTime.now());
        return veiculo;
    }

}
