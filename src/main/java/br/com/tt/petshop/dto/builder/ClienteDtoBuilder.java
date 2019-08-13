package br.com.tt.petshop.dto.builder;

import br.com.tt.petshop.dto.ClienteDto;

public class ClienteDtoBuilder {

    private ClienteDto dto = new ClienteDto();

    public ClienteDtoBuilder setId(Long id){
        dto.setId(id);
        return this;
    }

}
