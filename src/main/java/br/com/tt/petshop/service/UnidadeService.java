package br.com.tt.petshop.service;

import br.com.tt.petshop.model.Unidade;
import br.com.tt.petshop.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public List<Unidade> findAll(){
        return unidadeRepository.findAll();
    }

    public Optional<Unidade> findById(Long id){
        return unidadeRepository.findById(id);
    }

    public Unidade create(Unidade unidade){
        return unidadeRepository.save(unidade);
    }

    public void update(Unidade unidade, Long id) {
        unidadeRepository.save(unidade);
    }

    public void deleteById(Long id) {
        unidadeRepository.deleteById(id);
    }
}