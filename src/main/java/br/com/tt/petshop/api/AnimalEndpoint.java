package br.com.tt.petshop.api;

import br.com.tt.petshop.dto.AnimalDto;
import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.service.AnimalService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "animais")
@Api("Animal Controller")
public class AnimalEndpoint {

    private final AnimalService animalService;
    private final ModelMapper mapper;

    public AnimalEndpoint(AnimalService animalService, ModelMapper mapper) {
        this.animalService = animalService;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Lista os animais ativos no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de animais retornada com sucesso"),
            @ApiResponse(code = 400, message = "Parâmetros informados incorretamente")
    })
    public ResponseEntity<List<AnimalDto>> findAll(
            @ApiParam("id do cliente para filtro")
            @RequestParam Optional<Long> clienteId,
            @ApiParam("Nome do animal")
            @RequestParam Optional<String> nome){
        return ResponseEntity.ok(animalService.listar(clienteId, nome).stream()
                .map(animal -> mapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnimalDto> findById(@PathVariable Long id){
        Optional<Animal> animalOptional = animalService.findById(id);
        if(animalOptional.isPresent()){
            AnimalDto dto = mapper.map(animalOptional.get(), AnimalDto.class);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
