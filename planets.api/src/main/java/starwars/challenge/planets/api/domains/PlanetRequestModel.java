package starwars.challenge.planets.api.domains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(value = "Modelo de requisição de um Planeta", description = "Modelo para requisições de planetas")
public class PlanetRequestModel {

    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name is required")
    @ApiModelProperty(value = "Name do planeta", required = true)
    private String name;
    @NotEmpty(message = "Climate is required")
    @NotNull(message = "Climate is required")
    @ApiModelProperty(value = "Clima do planeta", required = true)
    private String climate;
    @NotEmpty(message = "Terrain is required")
    @NotNull(message = "Terrain is required")
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
