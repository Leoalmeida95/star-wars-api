package starwars.challenge.planets.api.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import starwars.challenge.planets.api.exceptions.StarWarsException;

@RequiredArgsConstructor


@Configuration
public class PlanetsValidation {

    public void verifyProperties(String name, String climate, String terrain){
        if(name == null || name.isEmpty()){
            throw new StarWarsException(
                    HttpStatus.BAD_REQUEST.value(),
                    "Name is required",
                    HttpStatus.BAD_REQUEST);
        }

        if(climate == null || climate.isEmpty()){
            throw new StarWarsException(
                    HttpStatus.BAD_REQUEST.value(),
                    "Climate is required",
                    HttpStatus.BAD_REQUEST);
        }

        if(terrain == null || terrain.isEmpty()){
            throw new StarWarsException(
                    HttpStatus.BAD_REQUEST.value(),
                    "Terrain is required",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
