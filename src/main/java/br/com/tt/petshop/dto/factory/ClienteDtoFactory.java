package br.com.tt.petshop.dto.factory;

import br.com.tt.petshop.dto.ClienteDto;
import br.com.tt.petshop.model.Cliente;

public class ClienteDtoFactory {

    public static ClienteDto from(Cliente cliente){
        ClienteDto dto = new ClienteDto();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());

        return dto;
    }

}
