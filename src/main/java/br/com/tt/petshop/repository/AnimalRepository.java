package br.com.tt.petshop.repository;

import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByClienteId(Long clienteId);

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