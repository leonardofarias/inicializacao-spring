package br.com.tt.petshop.service;

import br.com.tt.petshop.client.CreditoApiClient;
import br.com.tt.petshop.client.dto.CreditoDto;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private CreditoApiClient client;
    private CreditoDto dto;

    @Before
    public void setUp() throws BusinessException {
        dto = new CreditoDto();
        dto.setSituacao("NORMAL");

        clienteService = new ClienteService(clienteRepository, client);
    }

    @Test
    public void deveriaRetornarListaVazia() throws BusinessException {
        List<Cliente> clientes = clienteService.listar();

        assertNotNull("A lista não deveria ser nula", clientes);
        assertEquals("A lista deveria retornar nenhum cliente", 0, clientes.size());
    }

    @Test
    public void deveriaRetornarListaComClientes() throws BusinessException {
        // Arrange - Setup
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        listaCliente.add(new Cliente(1L, "Fulano", "000.111.222-33"));
        listaCliente.add(new Cliente(2L, "Sicrano", "000.123.222-33"));
        when(clienteRepository.findAll()).thenReturn(listaCliente);

        // Act - Execução
        List<Cliente> clientes = clienteService.listar();

        // Assert - Verificação
        assertEquals("Deveria retornar 2 clientes", 2, clientes.size());
        assertEquals("Deveria retornar o Fulano", "Fulano", clientes.get(0).getNome());
    }

    @Test
    public void deveriaRemoverComSucesso() {
        // Act
        clienteService.remover(12L);

        // Assert
        Cliente clienteDeletado = new Cliente(12L, null, null);
        verify(clienteRepository).delete(clienteDeletado);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveriaAdicionarComSucesso() throws BusinessException {
        when(client.verificaSituacao(anyString())).thenReturn(dto);

        Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", "01773449036");
        // Act
        clienteService.adicionar(clienteAdicionado);

        // Assert
        verify(clienteRepository).save(clienteAdicionado);
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeEstaVazio() {
        try {
            Cliente clienteAdicionado = new Cliente(1L, null, "01773449036");
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome esta vazio");
        } catch (BusinessException e) {
            assertEquals("Cliente inválido", e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeTemMenosDeDuasLetras() {
        try {
            Cliente clienteAdicionado = new Cliente(1L, "L", "01773449036");
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome tem menos de duas letras");
        } catch (BusinessException e) {
            assertEquals("Nome inválido", e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoNomeNaoTemSobrenomeeTemMaisDeDuasLetras() {
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Le", "01773449036");
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando nome não tem sobrenome e tem mais de duas letras");
        } catch (BusinessException e) {
            assertEquals(
                    "Nome deve conter nome e sobrenome e cada um deve conter mais de duas letras",
                    e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoCpfEstaVazio() {
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", null);
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando cpf está vazio");
        } catch (BusinessException e) {
            assertEquals("Cliente inválido", e.getMessage());
        }
    }

    @Test
    public void deveriaLancarExcecaoQuandoCpfEstaIncorreto() {
        try {
            Cliente clienteAdicionado = new Cliente(1L, "Leonardo Silva", "0177349");
            clienteService.adicionar(clienteAdicionado);

            fail("Deveria lançar exceção quando cpf está incorreto");
        } catch (BusinessException e) {
            assertEquals("CPF inválido", e.getMessage());
        }
    }

    @Test
    public void deveriaBuscarClienteComSucesso() {
        // Arrange - Setup
        Cliente cliente = new Cliente(1L, "Fulano", "000.111.222-33");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Cliente find = clienteService.buscarCliente(1L).get();

        // Assert
        assertNotNull("Deve retornar um cliente com sucesso", find);
        assertEquals("Cliente encontrado", cliente.getNome(), find.getNome());

    }

    @Test
    public void deveriaLancarExcecaoQuandoClienteEstaInadimplente() {
        Cliente cliente = new Cliente(12L, null, null, true);
        when(clienteRepository.findById(12l)).thenReturn(Optional.of(cliente));
        try {
            clienteService.validaClienteInadimplente(12L);
            fail("Deveria retornar cliente inadimplente");
        } catch (BusinessException e) {
            assertEquals("Cliente inadimplente", e.getMessage());
        }
        verify(clienteRepository).findById(12L);
    }

    @Test
    public void deveriaRetornarOkQuandoClienteEstaAdimplente() {
        Cliente cliente = new Cliente(1L, null, null);
        cliente.setInadimplente(true);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        try {
            clienteService.validaClienteInadimplente(1L);
            verify(clienteRepository).findById(1L);
        } catch (BusinessException e) {
            assertEquals("Cliente inadimplente", e.getMessage());
        }
    }
}
