package starwars.challenge.planets.api.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import starwars.challenge.planets.api.domains.Planet;

import java.util.ArrayList;
import java.util.Optional;

@Component
public interface PlanetsRepository extends MongoRepository<Planet, String>{
    @Query(value = "{'name' : :#{#name} }")
    Optional<ArrayList<Planet>> findByName(@Param("name") String name);
}
