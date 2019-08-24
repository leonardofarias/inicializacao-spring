package br.com.tt.petshop.api;

import br.com.tt.petshop.dto.ClienteDto;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
//@PreAuthorize("hasAuthority('boss')")
public class ClienteEndpoint {

    private final ClienteService clienteService;
    private final ModelMapper mapper;

    public ClienteEndpoint(ClienteService clienteService, ModelMapper mapper) {
        this.clienteService = clienteService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody @Valid ClienteDto clienteDto) throws BusinessException {
        Cliente cliente = clienteService.adicionar(mapper.map(clienteDto, Cliente.class));
        URI uri = URI.create(String.format("/clientes/%d", cliente.getId()));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDto>> findAll(){

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok(clienteService.listar().stream()
                .map(cliente -> mapper.map(cliente, ClienteDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> findById(@PathVariable Long id){
        Optional<Cliente> clienteOptional = clienteService.buscarCliente(id);
        if(clienteOptional.isPresent()){
            ClienteDto dto = mapper.map(clienteOptional.get(), ClienteDto.class);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody ClienteDto clienteDto){
        clienteService.update(mapper.map(clienteDto, Cliente.class));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity patch(@RequestBody ClienteDto clienteDto,
        @Param("nome") String nome, @Param("inadimplente") Boolean inadimplente){
        Cliente clienteEncontrado = clienteService.buscarCliente(clienteDto.getId()).get();
        if(!nome.isEmpty()){
            clienteEncontrado.setNome(nome);
        }

        if(inadimplente != null){
            clienteEncontrado.setInadimplente(inadimplente);
        }

        clienteService.update(clienteEncontrado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
