package br.com.tt.petshop.service;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public List<Animal> listar(Long clientId){
        return animalRepository.listar(clientId);
    }

    public List<EspecieEnum> listarEspecies(){
        return Arrays.asList(EspecieEnum.values());
    }

    public void adicionar(Animal animal) throws BusinessException {
        animalRepository.save(animal);
    }

    public void remover(Animal animal) {
        //TODO alterar no JPA
        animalRepository.delete(animal);
    }
}
