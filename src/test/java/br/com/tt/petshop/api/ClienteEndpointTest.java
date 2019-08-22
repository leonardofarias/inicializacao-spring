package br.com.tt.petshop.api;

import br.com.tt.petshop.config.ModelMapperConfig;
import br.com.tt.petshop.dto.ClienteDto;
import br.com.tt.petshop.dto.factory.ClienteDtoFactory;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ClienteEndpoint.class)
@Import(ModelMapperConfig.class)
public class ClienteEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

//    @MockBean
//    private ModelMapper mapper;

    @Test
    public void deveRetornarListaClientecomSucesso() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("[]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deveriaRetornarListaComClienteFulano() throws Exception {

        Mockito.when(clienteService.listar()).thenReturn(
                Arrays.asList(new Cliente(72L, "Fulano Silva", "00011122233")));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(CoreMatchers.equalTo(72)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(CoreMatchers.equalTo("Fulano Silva")))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deveriaCriarClienteComSucesso() throws Exception {

        Cliente clienteSalvar = new Cliente(56L, "Fulano Silva", "00011122233");

        Mockito.when(clienteService.adicionar(clienteSalvar)).thenReturn(clienteSalvar);

        byte[] objectToJson = new ObjectMapper().writeValueAsBytes(
                new Cliente(72L, "Fulano Silva", "00011122233"));

        ClienteDto clienteDto = ClienteDtoFactory.from(clienteSalvar);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/clientes")
                        .content(objectToJson)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(header().string("Location", "/clientes/56"))
        .andExpect(content().string(""));
    }

    @Test
    public void deveriaRetornarFulanoComSucesso() throws Exception {

        Cliente cliente = new Cliente(55L, "Fulano Silva", "00011122233");
        Mockito.when(clienteService.buscarCliente(55L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/55"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(CoreMatchers.equalTo(55)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(CoreMatchers.equalTo("Fulano Silva")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(CoreMatchers.equalTo("00011122233")))
                .andDo(MockMvcResultHandlers.print());
    }
}