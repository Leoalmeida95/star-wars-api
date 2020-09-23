package starwars.challenge.planets.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import starwars.challenge.planets.api.domain.Planet;

@Component
public interface PlanetsRepository extends MongoRepository<Planet, String>{
}
