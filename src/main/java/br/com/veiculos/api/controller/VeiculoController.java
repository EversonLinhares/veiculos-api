package br.com.veiculos.api.controller;

import br.com.veiculos.api.dto.request.VeiculoEditarRequestDTO;
import br.com.veiculos.api.dto.request.VeiculoRequestDTO;
import br.com.veiculos.api.dto.response.VeiculoResponseDTO;
import br.com.veiculos.core.modelmapper.MapperConvert;
import br.com.veiculos.domain.model.Veiculo;
import br.com.veiculos.domain.service.VeiculoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService service;
    private final MapperConvert mapperConvert;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculos encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não foi encontrado veiculos."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/por-fabricante",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<VeiculoResponseDTO>>> pesquisarVeiculosFabricante() {
        Map<String, List<VeiculoResponseDTO>> veiculos = service.pesquisarVeiculosFabricante();

        return ResponseEntity.status(veiculos.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(veiculos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculos encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não foi encontrado veiculos."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/por-decada",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<VeiculoResponseDTO>>> pesquisarPorDecada() {
        Map<String, List<VeiculoResponseDTO>> veiculos = service.pesquisarPorDecada();

        return ResponseEntity.status(veiculos.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(veiculos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculos encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não foi encontrado veiculos."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/filtro",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VeiculoResponseDTO>> pesquisarVeiculosFiltro(
            @RequestParam(name = "marca") String marca,
            @RequestParam(name = "ano") Integer ano,
            @RequestParam(name = "cor") String cor) {
        List<VeiculoResponseDTO> veiculos = service.pesquisarVeiculosFiltro(marca,ano,cor);
        return ResponseEntity.status(veiculos.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(veiculos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculo encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado um veiculo com o id informado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponseDTO> pesquisarPorId(@PathVariable Long id) {
        VeiculoResponseDTO veiculoResponseDTO = mapperConvert
                .mapEntityToDto(service.pesquisarPorId(id), VeiculoResponseDTO.class);
        return ResponseEntity.ok().body(veiculoResponseDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veiculo cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarVeiculo(
            @RequestBody @Valid VeiculoRequestDTO veiculoDTO) {
        Veiculo veiculo = service.cadastrarVeiculo(veiculoDTO);
        URI location = UriComponentsBuilder.fromUriString("/veiculos/{id}")
                .buildAndExpand(veiculo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculo atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PutMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponseDTO> editarVeiculo(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDTO veiculoRequestDTO) {
        VeiculoResponseDTO veiculoDTO = service.editarVeiculo(id,veiculoRequestDTO);
        return ResponseEntity.ok().body(veiculoDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculo atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PatchMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponseDTO> editarVeiculoParcial(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoEditarRequestDTO veiculoEditarRequestDTO) {
        VeiculoResponseDTO veiculoResponseDTO = service.editarVeiculoParcial(id,veiculoEditarRequestDTO);
        return ResponseEntity.ok().body(veiculoResponseDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculo excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "id") Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/qtd-nao-vendidos")
    public ResponseEntity<Integer> countNaoVendidos() {
        return ResponseEntity.ok().body(service.countNaoVendidos());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veiculos encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não foi encontrado veiculos."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/ultima-semana",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VeiculoResponseDTO>> pesquisarVeiculosUltimaSemana() {
        List<VeiculoResponseDTO> carros = service.pesquisarVeiculosUltimaSemana();
        return ResponseEntity.ok().body(carros);
    }

}
