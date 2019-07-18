package br.com.tt.petshop.service;

import br.com.tt.petshop.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    public List<Cliente> listar(){
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("Leonardo", "01773449036"));
        listaClientes.add(new Cliente("Juca", "02146188030"));

        return listaClientes;
    }
}
