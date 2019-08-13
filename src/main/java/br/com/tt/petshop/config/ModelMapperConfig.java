package br.com.tt.petshop.config;

import br.com.tt.petshop.dto.ClienteDto;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.model.vo.Cpf;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getBean(){
        ModelMapper modelMapper = new ModelMapper();

/*        modelMapper.createTypeMap(Cliente.class, ClienteDto.class)
                .addMapping(Cliente::getCpf, (clienteDto, o) -> clienteDto.setCpf(((Cpf)o).getValor()));

        modelMapper.createTypeMap(ClienteDto.class, Cliente.class)
                .addMapping(dto -> new Cpf(dto.getCpf()), Cliente::setCpf);*/

        modelMapper
                .createTypeMap(Cliente.class, ClienteDto.class)
                .addMapping(
                        cliente -> cliente.getCpf().getValor(),
                        ClienteDto::setCpf);

        modelMapper
                .createTypeMap(ClienteDto.class, Cliente.class)
                .addMapping(clienteDto -> new Cpf(clienteDto.getCpf()),
                Cliente::setCpf);
        return new ModelMapper();
    }

}
