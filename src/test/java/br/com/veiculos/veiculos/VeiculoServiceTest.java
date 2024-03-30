package br.com.veiculos.veiculos;

import br.com.veiculos.api.dto.request.VeiculoEditarRequestDTO;
import br.com.veiculos.api.dto.request.VeiculoRequestDTO;
import br.com.veiculos.api.dto.response.VeiculoResponseDTO;
import br.com.veiculos.core.modelmapper.MapperConvert;
import br.com.veiculos.domain.enums.MarcaEnum;
import br.com.veiculos.domain.exception.NegocioException;
import br.com.veiculos.domain.model.Veiculo;
import br.com.veiculos.domain.repository.VeiculoRepository;
import br.com.veiculos.domain.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.veiculos.veiculos.stub.VeiculoStub.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @Mock
    private MapperConvert mapperConvert;

    @InjectMocks
    private VeiculoService service;

    @Test
    void deveCadastrarVeiculoComSucesso() {
        VeiculoRequestDTO requestDTO = criarVeiculoRequestDTO();
        Veiculo veiculo = criarVeiculo();
        when(mapperConvert.mapDtoToEntity(requestDTO, Veiculo.class)).thenReturn(veiculo);
        when(repository.save(veiculo)).thenReturn(veiculo);

        Veiculo veiculoCadastrado = service.cadastrarVeiculo(requestDTO);

        assertNotNull(veiculoCadastrado);
        assertEquals(veiculo, veiculoCadastrado);
        verify(repository, times(1)).save(veiculo);
    }

    @Test
    void deveLancarExceptionQuandoMarcaInvalida() {
        VeiculoRequestDTO requestDTO = criarVeiculoRequestDTO();
        requestDTO.setMarca("testeMarca");

        assertThrows(NegocioException.class, () -> service.cadastrarVeiculo(requestDTO));
    }

    @Test
    void deveRetornarMapVeiculosPorFabricante() {
        Veiculo veiculo1 = criarVeiculo();
        Veiculo veiculo2 = criarVeiculo();
        veiculo2.setMarca(MarcaEnum.TOYOTA);
        List<Veiculo> veiculos = Arrays.asList(veiculo1, veiculo2);
        when(repository.findAll()).thenReturn(veiculos);

        var distribuicao = service.pesquisarVeiculosFabricante();

        assertFalse(distribuicao.isEmpty());
        assertTrue(distribuicao.containsKey(MarcaEnum.FORD.toString()));
        assertTrue(distribuicao.containsKey(MarcaEnum.TOYOTA.toString()));
        assertEquals(1, distribuicao.get(MarcaEnum.FORD.toString()).size());
        assertEquals(1, distribuicao.get(MarcaEnum.TOYOTA.toString()).size());
    }

    @Test
    void deveRetornarMapVeiculosPorDecada() {
        Veiculo veiculo1 = criarVeiculo();
        Veiculo veiculo2 = criarVeiculo();
        veiculo2.setAno(2005);
        List<Veiculo> veiculos = Arrays.asList(veiculo1, veiculo2);
        when(repository.findAll()).thenReturn(veiculos);

        var distribuicao = service.pesquisarPorDecada();

        assertFalse(distribuicao.isEmpty());
        assertTrue(distribuicao.containsKey("Década 2020"));
        assertTrue(distribuicao.containsKey("Década 2000"));
        assertEquals(1, distribuicao.get("Década 2020").size());
        assertEquals(1, distribuicao.get("Década 2000").size());
    }

    @Test
    void devPesquisarVeiculosFiltro() {
        String marca = "Ford";
        Integer ano = 2020;
        String cor = "Preto";

        when(repository.pesquisarComFiltro(any(MarcaEnum.class), eq(ano), eq(cor))).thenReturn(Arrays.asList(getVeiculoFiltro()));
        when(mapperConvert.collectionToDto(anyList(), eq(VeiculoResponseDTO.class))).thenReturn(Arrays.asList(new VeiculoResponseDTO()));

        List<VeiculoResponseDTO> result = service.pesquisarVeiculosFiltro(marca, ano, cor);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void devPesquisarPorId() {
        Long id = 1L;

        when(repository.findById(eq(id))).thenReturn(java.util.Optional.of(criarVeiculo()));

        Veiculo result = service.pesquisarPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void devEditarVeiculo() {
        Long id = 1L;

        when(repository.findById(eq(id))).thenReturn(java.util.Optional.of(getVeiculoEditar()));
        when(repository.save(any(Veiculo.class))).thenReturn(getVeiculoEditar());
        when(mapperConvert.mapEntityToDto(any(Veiculo.class), eq(VeiculoResponseDTO.class))).thenReturn(getVeiculoResponse());

        VeiculoResponseDTO result = service.editarVeiculo(id, getVeiculoRequestDTOEditar());

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Ford", result.getMarca());
        assertEquals(2020, result.getAno());
        assertEquals("Modelo", result.getVeiculo());
        assertEquals("Descrição", result.getDescricao());
    }

    @Test
    void devCountNaoVendidosQtd5() {
        int countEsperado = 5;
        when(repository.countNaoVendidos()).thenReturn(countEsperado);

        Integer result = service.countNaoVendidos();

        assertEquals(countEsperado, result);
    }

    @Test
    void devPesquisarVeiculosUltimaSemana() {
        when(repository.findByCreatedAfter(any())).thenReturn(getVeiculosUltimaSemana());
        when(mapperConvert.collectionToDto(anyList(),any())).thenReturn(Collections.singletonList(getVeiculoDTOUltimaSemana()));

        List<VeiculoResponseDTO> result = service.pesquisarVeiculosUltimaSemana();

        assertEquals(1, result.size());
    }

    @Test
    void devEditarVeiculoParcial() {
        Long id = 1L;
        VeiculoEditarRequestDTO veiculoDTO = new VeiculoEditarRequestDTO();
        veiculoDTO.setVendido(true);

        when(repository.findById(eq(id))).thenReturn(Optional.of(criarVeiculo()));
        when(repository.save(any(Veiculo.class))).thenReturn(criarVeiculo());
        when(mapperConvert.mapEntityToDto(any(),any())).thenReturn(getVeiculoResponseVendido());
        VeiculoResponseDTO result = service.editarVeiculoParcial(id, veiculoDTO);


        assertNotNull(result);
        assertTrue(result.getVendido());
        assertNotNull(result.getUpdated());
    }


}
