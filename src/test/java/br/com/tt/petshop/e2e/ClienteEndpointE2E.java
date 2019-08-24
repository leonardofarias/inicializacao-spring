package br.com.tt.petshop.e2e;

import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.ClienteRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@AutoConfigureTestDatabase
public class ClienteEndpointE2E {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @Sql(value = "classpath:e2e/insere_fulano.sql")
    public void obtemClienteFulanoComSucesso() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/135"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(CoreMatchers.equalTo(135)));
    }
}
