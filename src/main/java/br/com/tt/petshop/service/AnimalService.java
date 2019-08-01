package br.com.tt.petshop.service;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Animal;

import br.com.tt.petshop.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AnimalService {

    private static final int TAMANHO_MINIMO_FINAL = 3;
    private final AnimalRepository animalRepository;
    private final ClienteService clienteService;

    public AnimalService(AnimalRepository animalRepository, ClienteService clienteService){
        this.animalRepository = animalRepository;
        this.clienteService = clienteService;
    }

    public List<Animal> listar(Long clientId){
        return animalRepository.findByClientId(clientId);
    }

    public List<EspecieEnum> listarEspecies(){
        return Arrays.asList(EspecieEnum.values());
    }

    public void adicionar(Animal animal) throws BusinessException {
        validaNome(animal);
        validaDataNascimentoMenorIgualHoje(animal);
        clienteService.validaClienteInadimplente(animal.getClientId());

        animalRepository.save(animal);
    }

    private void validaDataNascimentoMenorIgualHoje(Animal animal) throws BusinessException {
        if (Objects.isNull(animal) || Objects.isNull(animal.getDataNascimento())){
            throw new BusinessException("Data inválida");
        }

        if(LocalDate.now().isBefore(animal.getDataNascimento())){
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
}
