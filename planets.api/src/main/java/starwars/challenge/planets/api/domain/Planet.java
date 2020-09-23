package starwars.challenge.planets.api.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Modelo de um Planeta", description = "A representação de cada planeta da galaxia")
public class Planet {
    @Id
    private String id;
    private String name;
    private String climate;
    private String terrain;

    public PlanetResponseModel _toConvertPlanetResponseModel(){
        PlanetResponseModel dto = PlanetResponseModel.builder()
                                    .id(this.id)
                                    .name(this.name)
                                    .climate(this.climate)
                                    .terrain(this.terrain)
                                    .build();
        return dto;
    }
}
