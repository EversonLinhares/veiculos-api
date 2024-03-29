package br.com.veiculos.domain.repository;

import br.com.veiculos.domain.enums.MarcaEnum;
import br.com.veiculos.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    @Query("""
            SELECT veiculo FROM Veiculo veiculo 
            WHERE veiculo.marca = :marca 
            AND veiculo.ano = :ano
            AND lower(veiculo.cor) like lower(concat(:cor, '%'))
            """)
    List<Veiculo> pesquisarComFiltro(
            @Param("marca") MarcaEnum marca,
            @Param("ano") Integer ano,
            @Param("cor") String cor);

    @Query("""
            SELECT count(id) FROM Veiculo veiculo WHERE veiculo.vendido = false
            """)
    Integer countNaoVendidos();

    List<Veiculo> findByCreatedAfter(LocalDateTime data);
}
