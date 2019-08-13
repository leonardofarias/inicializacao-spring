package br.com.tt.petshop.repository;

import br.com.tt.petshop.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Override
    void deleteById(Long aLong);
}
