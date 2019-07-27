package br.com.tt.petshop.service;

import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;

    @Before
    public void setUp(){
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    public void deveriaRetornarListaVazia() {

        clienteService = new ClienteService(clienteRepository);

        List<Cliente> clientes = clienteService.listar();

        assertNotNull("A lista não deveria ser nula", clientes);
        assertEquals("A lista deveria retornar nenhum cliente", 0, clientes.size());

    }

    @Test
    public void deveriaRetornarListaComClientes() {
        // Arrange - Setup
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        listaCliente.add(new Cliente(1L, "Fulano", "000.111.222-33", false));
        listaCliente.add(new Cliente(2L, "Sicrano", "000.123.222-33", true));
        when(clienteRepository.findAll()).thenReturn(listaCliente);

        // Act - Execução
        List<Cliente> clientes = clienteService.listar();

        // Assert - Verificação
        assertEquals("Deveria retornar 2 clientes", 2, clientes.size());
        assertEquals("Deveria retornar o Fulano","Fulano", clientes.get(0).getNome());
    }

    @Test
    public void deveriaRemoverComSucesso() {
        //Act
        clienteService.remover(12L);

        //Assert
        Cliente clienteDeletado = new Cliente(12L, null, null, false);
        verify(clienteRepository).delete(clienteDeletado);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveriaAdicionarComSucesso() throws BusinessException {
        //Act
        Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", "01773449036", false);
        clienteService.adicionar(clienteAdicionado);

        //Assert
        verify(clienteRepository).save(clienteAdicionado);
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeEstaVazio(){
        try {
            Cliente clienteAdicionado = new Cliente(1L, null, "01773449036", false);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome esta vazio");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Cliente inválido");
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeTemMenosDeDuasLetras(){
        try {
            Cliente clienteAdicionado = new Cliente(1L, "L", "01773449036", false);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome tem menos de duas letras");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Nome inválido");
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeNaoTemSobrenomeeTemMaisDeDuasLetras(){
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Le", "01773449036", false);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome não tem sobrenome e tem mais de duas letras");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Nome deve conter nome e sobrenome e cada um deve conter mais de duas letras");
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoCpfEstaVazio(){
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", null, false);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando cpf está vazio");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Cliente inválido");
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoCpfEstaIncorreto(){
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", "0177349", false);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando cpf está incorreto");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "CPF inválido");
        }
    }

    @Test
    public void deveriaBuscarClienteComSucesso() {
        clienteService.buscarCliente(12L);

        Cliente clienteBuscar = new Cliente(12L, null, null, false);
        verify(clienteRepository).findById(clienteBuscar.getId());
    }

    @Test
    public void deveriaLancarExcecaoQuandoClienteEstaInadimplente(){
        Cliente cliente = new Cliente(12L, null, null, true);
        when(clienteRepository.findById(12l)).thenReturn(cliente);
        try {
            clienteService.validaClienteInadimplente(12L);

            fail("Deveria retornar cliente inadimplente");
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Cliente inadimplente");
        }
        verify(clienteRepository).findById(12L);
    }

    @Test
    public void deveriaRetornarOkQuandoClienteEstaAdimplente(){
        Cliente cliente = new Cliente(12L, null, null, false);
        when(clienteRepository.findById(12l)).thenReturn(cliente);
        try {
            clienteService.validaClienteInadimplente(12L);

            verify(clienteRepository).findById(12L);
        } catch (BusinessException e) {
            assertEquals(e.getMessage(), "Cliente inadimplente");
        }
        verify(clienteRepository).findById(12L);
    }

}