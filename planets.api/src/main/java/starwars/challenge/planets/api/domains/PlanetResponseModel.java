package starwars.challenge.planets.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Modelo de resposta de um Planeta", description = "Modelo para resposta de planetas")
public class PlanetResponseModel {
    private String id;
    private String name;
    private String climate;
    private String terrain;
}
