package br.com.tt.petshop.repository;

import br.com.tt.petshop.model.Animal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnimalRepositoryIT {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void deveriaRetornarListaVazia(){

        List<Animal> lista = animalRepository.findByClienteId(1L);

        Assert.assertEquals("Deveria ser lista vazia", 0, lista.size());
    }

}