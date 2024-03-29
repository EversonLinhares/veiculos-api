package br.com.veiculos.domain.service;

import br.com.veiculos.api.dto.request.VeiculoEditarRequestDTO;
import br.com.veiculos.api.dto.request.VeiculoRequestDTO;
import br.com.veiculos.api.dto.response.VeiculoResponseDTO;
import br.com.veiculos.core.modelmapper.MapperConvert;
import br.com.veiculos.domain.enums.MarcaEnum;
import br.com.veiculos.domain.exception.NegocioException;
import br.com.veiculos.domain.exception.ObjectNotFoundException;
import br.com.veiculos.domain.model.Veiculo;
import br.com.veiculos.domain.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository repository;
    private final MapperConvert mapperConvert;

    public Veiculo cadastrarVeiculo(VeiculoRequestDTO veiculoDTO) {
        Veiculo veiculo = mapperConvert.mapDtoToEntity(veiculoDTO, Veiculo.class);
        veiculo.setMarca(MarcaEnum.findByNome(veiculoDTO.getMarca()));
        veiculo.setCreated(LocalDateTime.now());
        return repository.save(veiculo);
    }

    public Map<String, List<VeiculoResponseDTO>> pesquisarVeiculosFabricante() {
        List<Veiculo> veiculos = repository.findAll();
        Map<String, List<VeiculoResponseDTO>> distribuicao = new HashMap<>();

        for (Veiculo veiculo : veiculos) {
            String fabricante = veiculo.getMarca().toString();
            VeiculoResponseDTO veiculoDTO = mapperConvert.mapEntityToDto(veiculo, VeiculoResponseDTO.class);
            distribuicao.computeIfAbsent(fabricante, k -> new ArrayList<>()).add(veiculoDTO);
        }

        return distribuicao;
    }

    public Map<String, List<VeiculoResponseDTO>> pesquisarPorDecada() {
        List<Veiculo> veiculos = repository.findAll();

        return veiculos.stream()
                .collect(Collectors.groupingBy(
                        veiculo -> {
                            int ano = veiculo.getAno();
                            int decada = ano / 10 * 10;
                            return "Década " + decada;
                        }, Collectors.mapping(
                                veiculo -> mapperConvert.mapEntityToDto(veiculo, VeiculoResponseDTO.class),
                                Collectors.toList()
                        )
                ));
    }


    public List<VeiculoResponseDTO> pesquisarVeiculosFiltro(String marca, Integer ano, String cor) {
        List<Veiculo> veiculos = repository.pesquisarComFiltro(MarcaEnum.findByNome(marca),ano,cor);
        return mapperConvert.collectionToDto(veiculos,VeiculoResponseDTO.class);
    }

    public Veiculo pesquisarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Veiculo não encontrado com id : " + id));
    }

    public VeiculoResponseDTO editarVeiculo(Long id, VeiculoRequestDTO veiculoRequestDTO) {
        Veiculo veiculoBanco = pesquisarPorId(id);
        validaVeiculoVendido(veiculoBanco);
        mapperConvert.copy(veiculoRequestDTO,veiculoBanco);
        veiculoBanco.setMarca(MarcaEnum.findByNome(veiculoRequestDTO.getMarca()));
        veiculoBanco.setUpdated(LocalDateTime.now());
        return mapperConvert.mapEntityToDto(repository.save(veiculoBanco), VeiculoResponseDTO.class);
    }

    public VeiculoResponseDTO editarVeiculoParcial(Long id, VeiculoEditarRequestDTO veiculoDTO) {
        Veiculo veiculoBanco = pesquisarPorId(id);
        validaVeiculoVendido(veiculoBanco);
        veiculoBanco.setVendido(veiculoDTO.getVendido());
        veiculoBanco.setUpdated(LocalDateTime.now());
        return mapperConvert.mapEntityToDto(repository.save(veiculoBanco),VeiculoResponseDTO.class);
    }

    public void deletar(Long id) {
        Veiculo veiculo = pesquisarPorId(id);
        repository.delete(veiculo);
    }

    private void validaVeiculoVendido(Veiculo veiculo) {
        if(veiculo.getVendido()) {
            throw new NegocioException("Veículo vendido não pode ser editado.");
        }
    }

    public Integer countNaoVendidos() {
        return repository.countNaoVendidos();
    }

    public List<VeiculoResponseDTO> pesquisarVeiculosUltimaSemana() {
        LocalDateTime data = LocalDateTime.now().minusWeeks(1);
        List<Veiculo> veiculos = repository.findByCreatedAfter(data);
        return mapperConvert.collectionToDto(veiculos, VeiculoResponseDTO.class);
    }
}
