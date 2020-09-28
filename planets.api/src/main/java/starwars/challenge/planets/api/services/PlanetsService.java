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
import java.util.Optional;

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

    public PlanetResponseModel findById(String id) throws StarWarsException{

        Optional<Planet> optional = planetsRepository.findById(id);
        Planet planet = optional.get();

        return planet._toConvertPlanetResponseModel();
    }

    public List<PlanetResponseModel> findByName(String name){

        Optional<ArrayList<Planet>> optional = planetsRepository.findByName(name);
        ArrayList<Planet> planets = optional.get();
        List<PlanetResponseModel> responses = new ArrayList<PlanetResponseModel>();

        for(Planet planet: planets){
            PlanetResponseModel response = planet._toConvertPlanetResponseModel();
            responses.add(response);
        }

        return responses;
    }

    public void delete(String id) throws StarWarsException {
        Optional<Planet> optional = planetsRepository.findById(id);
        Planet planet =  optional.get();
        planetsRepository.delete(planet);
    }

    public PlanetResponseModel add(PlanetRequestModel model) throws StarWarsException {

        Planet planet = model._toConvertPlanet();

        planetsRepository.insert(planet);

        return planet._toConvertPlanetResponseModel();
    }
}
