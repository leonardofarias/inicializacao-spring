package br.com.tt.petshop.repository;

import br.com.tt.petshop.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query("select distinct unidade From Animal a Where a.nome = :nome")
    List<Unidade> findByAnimaisNome(@Param("nome") String nome);
}
