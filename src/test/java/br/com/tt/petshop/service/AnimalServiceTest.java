package br.com.tt.petshop.service;

import br.com.tt.petshop.dto.ClienteDto;
import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.model.vo.DataNascimento;
import br.com.tt.petshop.repository.AnimalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnimalServiceTest {

    private AnimalService animalService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ModelMapper mapper;

    private Cliente cliente;

    @Before
    public void setUp() {

        animalService = new AnimalService(animalRepository, clienteService, mapper);

        cliente = new Cliente(1L, "Fulano", "000.111.222-33");
    }

    @Test
    public void deveriaRetornarListaVazia() {
        List<Animal> animais = animalService.listar(Optional.empty(), Optional.empty());

        assertNotNull("A lista não deveria ser nula", animais);
        assertEquals("A lista deveria retornar nenhum animal", 0, animais.size());
    }

    @Test
    public void deveriaRetornarListaComAnimais() {
        // Arrange - Setup
//        List<Animal> listaAnimais = new ArrayList<>(Arrays.asList(
//                new Animal(0L, "Rex", LocalDate.now(), EspecieEnum.MAMIFERO, 1L)));
//        when(animalRepository.findAll()).thenReturn(listaAnimais);
//
//        // Act - Execução
//        List<Animal> animais = animalService.listar(1L);
//
//        // Assert - Verificação
//        assertEquals("Deveria retornar 1 animal", 1, animais.size());
//        assertEquals("Deveria retornar o Rex", "Rex", animais.get(0).getNome());
    }

    @Test
    public void deveriaRetornarUmaListaDeEspecies() {

        List<EspecieEnum> especies = animalService.listarEspecies();

        assertEquals("Deveria retornar 3 espécies", 3, especies.size());
    }

    @Test
    public void deveriaAdicionarComSucesso() throws BusinessException {
        Animal animalAdicionado = new Animal("TOTO", LocalDate.now(), EspecieEnum.MAMIFERO,0L);

        // Act
        animalService.adicionar(animalAdicionado);

        // Assert
        verify(animalRepository).save(animalAdicionado);
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeEstaVazio() {
        try {
            Animal animalAdicionado = new Animal(null, LocalDate.now(), EspecieEnum.MAMIFERO, 0L);
            animalService.adicionar(animalAdicionado);

            fail("Deveria lançar exceção quando nome esta vazio");
        } catch (BusinessException e) {
            assertEquals("Nome inválido", e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeTemMenosQueUmTamanhoMinimoDefinido() {
        try {
            Animal animalAdicionado = new Animal("T", LocalDate.now(), EspecieEnum.MAMIFERO, 0l);
            animalService.adicionar(animalAdicionado);

            fail("Deveria lançar exceção quando nome tem menos de 3 letras");
        } catch (BusinessException e) {
            assertEquals(String.format("Nome deve conter mais de %d letras", 3),e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoDataNascimentoEstaVazia() {
        try {
            Animal animalAdicionado = new Animal("TOTO", null, EspecieEnum.MAMIFERO, 0L);
            animalService.adicionar(animalAdicionado);

            fail("Deveria lançar exceção quando data está vazia");
        } catch (BusinessException e) {
            assertEquals("Data inválida",e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoDataSuperiorHoje() {
        try {
            Animal animalAdicionado = new Animal("TOTO", LocalDate.now().plusDays(1L), EspecieEnum.MAMIFERO, 0l);
            animalService.adicionar(animalAdicionado);

            fail("Deveria lançar exceção quando data está superior a Hoje");
        } catch (BusinessException e) {
            assertEquals("Data não pode ser superior a hoje",e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoAoSalvarAnimalQuandoClienteEstaInadimplente() throws BusinessException {
        //EM MÉTODOS VOID PARA RETORNAR EXCEÇÃO
        //Given
        doThrow(BusinessException.class).when(clienteService).validaClienteInadimplente(cliente.getId());
        Animal animal = null;
        try {
            animal = new Animal("TOTO", LocalDate.now(), EspecieEnum.MAMIFERO, 0L);
            //When
            animalService.adicionar(animal);
            fail("Deveria lançar exceção quando cliente esta inadimplente");
        } catch (BusinessException e) {
            //Then
            assertNotNull(e);
            verify(animalRepository, times(0)).save(animal);
            verify(clienteService, times(1)).validaClienteInadimplente(cliente.getId());
        }
    }

    @Test
    public void deveriaRemoverComSucesso() {
        // Act
        animalService.remover(new Animal("Toto", LocalDate.now(), EspecieEnum.MAMIFERO, 0L));

        // Assert
        Animal animalDeletado = new Animal("Toto", LocalDate.now(), EspecieEnum.MAMIFERO, 0L);
        verify(animalRepository).delete(animalDeletado);
        verifyNoMoreInteractions(animalRepository);
    }
}
