package br.com.tt.petshop.service;

import br.com.tt.petshop.dto.AnimalDto;
import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.exception.ClientNotFoundException;
import br.com.tt.petshop.model.Animal;

import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.model.vo.DataNascimento;
import br.com.tt.petshop.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class AnimalService {

    private static final int TAMANHO_MINIMO_FINAL = 3;
    private final AnimalRepository animalRepository;
    private final ClienteService clienteService;
    private final ModelMapper mapper;


    public AnimalService(AnimalRepository animalRepository, ClienteService clienteService, ModelMapper mapper) {
        this.animalRepository = animalRepository;
        this.clienteService = clienteService;
        this.mapper = mapper;
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

    /**
     * @deprecated Utilizar o salvar(animalDto) que possui o id do cliente
     **/
    public Animal adicionar(Animal animal) throws BusinessException {
        validaNome(animal.getNome());
        validaDataNascimentoMenorIgualHoje(animal.getDataNascimento());
        clienteService.validaClienteInadimplente(animal.getCliente().getId());

        return animalRepository.save(animal);
    }

    private void validaDataNascimentoMenorIgualHoje(DataNascimento data) throws BusinessException {
        if (Objects.isNull(data)){
            throw new BusinessException("Data inválida");
        }

        if(data.isValid()){
            throw new BusinessException("Data não pode ser superior a hoje");
        }

    }

    private void validaNome(String nome) throws BusinessException {
        if (Objects.isNull(nome) || Objects.isNull(nome)) {
            throw new BusinessException("Nome inválido");
        }

        if (nome.trim().length() < TAMANHO_MINIMO_FINAL) {
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

    public Animal salvar(@NotNull @Valid AnimalDto animalDto) throws BusinessException {
        Optional<Cliente> cliente = clienteService.buscarCliente(animalDto.getClienteId());

        Animal animal = mapper.map(animalDto, Animal.class);
        //animal.setCliente(cliente.orElseThrow(ClientNotFoundException::new));
        animal.setCliente(cliente.orElseThrow(() -> new ClientNotFoundException(animalDto.getClienteId())));
        return adicionar(animal);
    }
}
