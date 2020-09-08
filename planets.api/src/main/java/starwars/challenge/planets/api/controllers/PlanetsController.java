package starwars.challenge.planets.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiOperation(value = "Tudo sobre os mais incríveis planetas do Star Wars", response = PlanetsController.class)
public class PlanetsController {
    @ApiOperation(value = "Retorna uma string",
            notes="Teste básico de endpoint da api.",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma string", response = String.class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhuma planeta foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar os planetas"),
    })
    @GetMapping( value = "/planets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity find(){
        try{
            return Optional.ofNullable("Hello World")
                    .map(x -> ResponseEntity.ok().body("Hello World"))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (Exception ex){
            log.error("Error getting transactions ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
