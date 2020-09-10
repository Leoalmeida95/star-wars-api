package starwars.challenge.planets.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Setter
@Getter
@ApiModel(value = "Modelo de requisição de um Planeta", description = "Modelo para requisições de planetas")
public class PlanetRequestModel {
    public PlanetRequestModel(){}

    PlanetRequestModel(String name){
        this.name = name;
    }

    @ApiModelProperty(value = "Name do planeta", required = true)
    private String name;
}
