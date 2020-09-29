package starwars.challenge.planets.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starwars.challenge.planets.api.domain.PlanetRequestModel;
import starwars.challenge.planets.api.domain.PlanetResponseModel;
import starwars.challenge.planets.api.exceptions.StarWarsException;
import starwars.challenge.planets.api.services.PlanetsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiOperation(value = "Tudo sobre os mais incríveis planetas do Star Wars", response = PlanetsController.class)
@RequestMapping(path="/api/planets", produces=MediaType.APPLICATION_JSON_VALUE)
public class PlanetsController {

    private final PlanetsService planetsService;

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "Retorna todos os Planetas do universo",
            notes="Todos os planetas dos mais variados tipos e características",
            response = PlanetResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todos os planetas retornados", response = PlanetResponseModel.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar os planetas"),
    })
    public ResponseEntity findAll(){
        try{

            List<PlanetResponseModel> planets = planetsService.findAll();

            return Optional.ofNullable(planets)
                    .map(x -> ResponseEntity.ok().body(planets))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (StarWarsException ex){
            return ResponseEntity.status(ex.getStatusCode()).body(ex.toString());
        }
        catch (Exception ex){
            log.error("Error getting all planets", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    @ApiOperation(value = "Retorna um Planeta por meio de uma identificação",
            notes="Cada Planeta tem um identificador próprio e único",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Um planeta retornado", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o planeta"),
    })
    public ResponseEntity findById(@PathVariable(name = "id", required = true)
                                       @ApiParam(value = "id", example = "1000", name= "Identificação do planeta", required = true ) String id){
        try{

            PlanetResponseModel planet = planetsService.findById(id);

            return Optional.ofNullable(planet)
                    .map(x -> ResponseEntity.ok().body(planet))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (StarWarsException ex){
            log.error("Error getting planet by id", ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.toString());
        }
        catch (Exception ex){
            log.error("Error getting planet by id", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }

    @GetMapping(params="name")
    @ResponseBody
    @ApiOperation(value = "Retorna um planeta por meio de um nome",
            notes="Cada Planeta tem um nome próprio e único",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Um planeta retornado", response = String.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o planeta"),
    })
    public ResponseEntity findByName(@RequestParam(name = "name", required = true)
                                         @ApiParam(value = "name", example = "Estrela da Morte", name= "Nome do Planeta", required = true ) String name){
        try{

            List<PlanetResponseModel> planets = planetsService.findByName(name);

            return Optional.ofNullable(planets)
                    .map(x -> ResponseEntity.ok().body(planets))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (StarWarsException ex){
            log.error("Error getting planet by name", ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.toString());
        }
        catch (Exception ex){
            log.error("Error getting planet by name", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }

    @DeleteMapping(value = "{id}")
    @ResponseBody
    @ApiOperation(value = "Destrói um planeta inteiro",
            notes="O planeta será destruído por meio da sua identificação",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Um plante foi removido", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhum planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o planeta"),
    })
    public ResponseEntity delete(@PathVariable(name = "id", required = true)
                                     @ApiParam(value = "id", example = "1000", name= "Identificação do planeta", required = true ) String id){
        try{
            planetsService.delete(id);
            return ResponseEntity.ok().build();
        }
        catch (StarWarsException ex){
            log.error("Error deleting an planet by id", ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.toString());
        }
        catch (Exception ex){
            log.error("Error deleting an planet by id", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }

    @ApiOperation(value = "Cria um novo planeta",
            notes="O planeta terá um Nome, Clima e Terreno",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Um novo planeta foi criado", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o planeta"),
    })
    @PostMapping
    public ResponseEntity add(@Valid @RequestBody PlanetRequestModel request){
        try{

            PlanetResponseModel result = planetsService.add(request);
            return Optional.ofNullable(result)
                    .map(m -> {
                        return ResponseEntity.status(HttpStatus.CREATED).body(result);
                    })
                    .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        catch (StarWarsException ex){
            log.error("Error adding an planet", ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.toString());
        }
        catch (Exception ex){
            log.error("Error adding an planet", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }
}
