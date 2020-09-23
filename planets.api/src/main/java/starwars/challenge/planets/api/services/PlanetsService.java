package starwars.challenge.planets.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import starwars.challenge.planets.api.domain.Planet;
import starwars.challenge.planets.api.domain.PlanetRequestModel;
import starwars.challenge.planets.api.domain.PlanetResponseModel;
import starwars.challenge.planets.api.exceptions.StarWarsException;
import starwars.challenge.planets.api.repository.PlanetsRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanetsService {

    private final PlanetsRepository planetsRepository;

    public List<PlanetResponseModel> findAll(){

        List<Planet> planets = planetsRepository.findAll();
        List<PlanetResponseModel> responses = new ArrayList<PlanetResponseModel>();

        for(Planet planet: planets){
            PlanetResponseModel response = planet._toConvertPlanetResponseModel();
            responses.add(response);
        }

        return responses;
    }

    public String findById(Integer id){

        String planet = "";

        return planet;
    }

    public String findByName(String name){

        String planet = "";

        return planet;
    }

    public void delete(Integer id) throws StarWarsException {

    }

    public String add(PlanetRequestModel model) throws StarWarsException {

        return  "";
    }
}
