package br.com.tt.petshop.repository;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.model.Animal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(value = "classpath:limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:insere_rex.sql")
@ActiveProfiles("test-jpa")
public class AnimalRepositoryIT {

    @Before
    public void setUp(){
    }

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void deveriaRetornarListaVazia(){

        List<Animal> lista = animalRepository.findByClienteId(1L);

        Assert.assertEquals("Deveria ser lista vazia", 0, lista.size());
    }

    @Test
    public void deveriaRetornarUmAnimal() {
        List<Animal> list = animalRepository.findByClienteId(133L);
        Assert.assertEquals("Deveria retornar um animal", 2, list.size());
        Animal rex = list.get(0);
        Assert.assertEquals("O nome deveria ser Rex", "Rex", rex.getNome());
        Assert.assertEquals("O cliente deveria ser o 133", Long.valueOf(133), rex.getCliente().getId());
        Assert.assertEquals("Deveria ser um mamífero", EspecieEnum.MAMIFERO, rex.getEspecie());
    }

    @Test
    public void deveriaRetornarUmAnimalBuscandoPeloNomeAnimal() {
        List<Animal> list = animalRepository.findByNome("Rex");
        Assert.assertEquals("Deveria retornar um animal", 1, list.size());
        Animal rex = list.get(0);
        Assert.assertEquals("O nome deveria ser Rex", "Rex", rex.getNome());
        Assert.assertEquals("O cliente deveria ser o 133", Long.valueOf(133), rex.getCliente().getId());
        Assert.assertEquals("Deveria ser um mamífero", EspecieEnum.MAMIFERO, rex.getEspecie());
    }

    @Test
    public void deveriaRetornarUmaListaDeAnimaisInformandoDataInicioDataFimEEspecie() {
        LocalDate dataInicio = LocalDate.parse("2019-08-01");
        LocalDate dataFim = LocalDate.parse("2019-09-01");
        List<Animal> list = animalRepository.
                findByDataNascimentoDataBetweenAndEspecie(dataInicio, dataFim, EspecieEnum.MAMIFERO);
        Assert.assertEquals("Deveria retornar pelo menos um animal", 1, list.size());
    }

    @Test
    public void deveriaRetornarUmaListadeAnimaisDeTodosClientesDeUmaUnidade(){
        List<Animal> list = animalRepository.findByClienteUnidadeId(1L);
        Assert.assertEquals("Deveria retornar quatro animais", 4, list.size());
    }
}

