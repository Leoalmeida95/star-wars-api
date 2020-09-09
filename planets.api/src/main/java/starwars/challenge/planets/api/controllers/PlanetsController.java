package starwars.challenge.planets.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import starwars.challenge.planets.api.services.PlanetsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiOperation(value = "Tudo sobre os mais incríveis planetas do Star Wars", response = PlanetsController.class)
public class PlanetsController {

    private final PlanetsService planetsService;

    @ApiOperation(value = "Retorna todos os planetas do universo",
            notes="Todos os planetas dos mais variados tipos e características",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de planetas", response = String[].class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar os planetas"),
    })
    @GetMapping( value = "/planets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAll(){
        try{

            List<String> planets = planetsService.findAll();

            return Optional.ofNullable(planets)
                    .map(x -> ResponseEntity.ok().body(planets))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (Exception ex){
            log.error("Error getting planets ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @ApiOperation(value = "Retorna um planeta incrível",
            notes="Tudo que se precisa saber sobre um planeta",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um planeta", response = String.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o planeta"),
    })
    @GetMapping( value = "/planets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable(value = "id") @ApiParam(value = "id", example = "1000", name= "Id do usuário", required = true ) Integer id){
        try{

            String planet = planetsService.findById(id);

            return Optional.ofNullable(planet)
                    .map(x -> ResponseEntity.ok().body(planet))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (Exception ex){
            log.error("Error getting planet ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
