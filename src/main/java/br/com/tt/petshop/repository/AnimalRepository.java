package br.com.tt.petshop.repository;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.model.projection.AnimalSimples;
import br.com.tt.petshop.model.vo.DataNascimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByClienteId(Long clienteId);

    List<Animal> findByNome(String nome);

    List<Animal> findByDataNascimentoDataBetweenAndEspecie(LocalDate dataInicio, LocalDate dataFim, EspecieEnum especie);

    List<Animal> findByClienteIdAndNome(Long clienteId, String nome);

    List<Animal> findByClienteIdAndNomeOrderByNome(Long aLong, String s);

    List<Animal> findByClienteUnidadeId(Long unidadeId);

    List<AnimalSimples> findByDataNascimentoData(LocalDate dataNascimento);

//    List<Animal> animais = new ArrayList<>(Arrays.asList(
//            new Animal(idAnimal, "Rex", LocalDate.now(), EspecieEnum.MAMIFERO, 1L),
//            new Animal(idAnimal, "Nemo", LocalDate.now().minusMonths(1), EspecieEnum.PEIXE, 2L)
//    ));
//
//    public List<Animal> listar(Long clientId) {
//        List<Animal> animaisDoCliente = new ArrayList<>();
//        for (Animal animal: animais) {
//            if(animal.getClientId().equals(clientId)){
//                animaisDoCliente.add(animal);
//            }
//        }
//        return animaisDoCliente;
//    }
//
//    public void save(Animal animal) {
//        animais.add(animal);
//    }
//
//    public void delete(Animal animal){
//        animais.remove(animal);
//    }
//
}