package br.com.tt.petshop.service;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Animal;

import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.AnimalRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnimalService {

    private static final int TAMANHO_MINIMO_FINAL = 3;
    private final AnimalRepository animalRepository;
    private final ClienteService clienteService;

    public AnimalService(AnimalRepository animalRepository, ClienteService clienteService){
        this.animalRepository = animalRepository;
        this.clienteService = clienteService;
    }

    public List<Animal> listar(Optional<Long> clienteId, Optional<String> nome){
        if(clienteId.isPresent() && nome.isPresent()){
            return animalRepository.findByClienteIdAndNomeOrderByNome(clienteId.get(), nome.get());
        }else if(clienteId.isPresent()){
            return animalRepository.findByClienteId(clienteId.get());
        }else if(nome.isPresent()){
            return animalRepository.findByNome(nome.get());
        }
        return animalRepository.findAll();
    }

    public List<Animal> listarByExample(Optional<Long> clienteId, Optional<String> nome){

        Animal animal = new Animal();
        if(clienteId.isPresent()){
            animal.setCliente(new Cliente(clienteId.get()));
        }
        if(nome.isPresent()){
            animal.setNome(nome.get());
        }

        return animalRepository.findAll(Example.of(animal), Sort.by("nome"));
    }

    public List<EspecieEnum> listarEspecies(){
        return Arrays.asList(EspecieEnum.values());
    }

    public void adicionar(Animal animal) throws BusinessException {
        validaNome(animal);
        validaDataNascimentoMenorIgualHoje(animal);
        clienteService.validaClienteInadimplente(animal.getCliente().getId());

        animalRepository.save(animal);
    }

    private void validaDataNascimentoMenorIgualHoje(Animal animal) throws BusinessException {
        if (Objects.isNull(animal) || Objects.isNull(animal.getDataNascimento().getData())){
            throw new BusinessException("Data inválida");
        }

        if(animal.getDataNascimento().isValid()){
            throw new BusinessException("Data não pode ser superior a hoje");
        }

    }

    private void validaNome(Animal animal ) throws BusinessException {
        if (Objects.isNull(animal) || Objects.isNull(animal.getNome())) {
            throw new BusinessException("Nome inválido");
        }

        if (animal.getNome().trim().length() < TAMANHO_MINIMO_FINAL) {
            throw new BusinessException(
                    String.format("Nome deve conter mais de %d letras", TAMANHO_MINIMO_FINAL));
        }
    }

    public void remover(Animal animal) {
        //TODO alterar no JPA
        animalRepository.delete(animal);
    }

    public Optional<Animal> findById(Long id){
        return animalRepository.findById(id);
    }
}
