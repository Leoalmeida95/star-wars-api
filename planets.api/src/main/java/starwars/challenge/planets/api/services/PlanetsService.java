package starwars.challenge.planets.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.json.JSONException;
import org.json.JSONObject;
import starwars.challenge.planets.api.exceptions.StarWarsException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanetsService {

    public List<String> findAll(){

        List<String> planets = new ArrayList<>();

        return planets;
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
}
