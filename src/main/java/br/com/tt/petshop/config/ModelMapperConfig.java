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
        System.out.println("Iniciei o modelmapper");

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .createTypeMap(Cliente.class, ClienteDto.class)
                .addMapping(
                        cliente -> cliente.getCpf().getValor(),
                        ClienteDto::setCpf);
//        .addMapping(Cliente::getCpf, (clienteDto, o) ->
//            clienteDto.setCpf(
//                    ((Cpf)o).getValor()
//            )
//        );

        modelMapper
                .createTypeMap(ClienteDto.class, Cliente.class)
                .addMapping(
                        ClienteDto::getCpf,
                        (cliente, o) -> cliente.getCpf().setValor((String) o));

        return modelMapper;
    }

}
