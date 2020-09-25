package starwars.challenge.planets.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Modelo de requisição de um Planeta", description = "Modelo para requisições de planetas")
public class PlanetRequestModel {

    @ApiModelProperty(value = "Name do planeta", required = true)
    private String name;
    @ApiModelProperty(value = "Clima do planeta", required = true)
    private String climate;
    @ApiModelProperty(value = "Terreno do planeta", required = true)
    private String terrain;

    PlanetRequestModel(){}

    PlanetRequestModel(String name, String climate, String terrain){
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet _toConvertPlanet(){
        Planet planet = Planet.builder()
                .name(this.name)
                .climate(this.climate)
                .terrain(this.terrain)
                .build();
        return planet;
    }
}
