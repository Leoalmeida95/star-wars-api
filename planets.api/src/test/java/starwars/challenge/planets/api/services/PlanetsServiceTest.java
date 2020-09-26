package starwars.challenge.planets.api.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starwars.challenge.planets.api.domain.Planet;
import starwars.challenge.planets.api.domain.PlanetRequestModel;
import starwars.challenge.planets.api.domain.PlanetResponseModel;
import starwars.challenge.planets.api.repository.PlanetsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PlanetsServiceTest {

    private MockMvc mockMvc;
    PlanetsService planetsService;
    PlanetsRepository mockService = mock(PlanetsRepository.class);

    @Before
    public void setUp() {
        planetsService = new PlanetsService(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(planetsService).build();
    }

    @Test
    public void TestShouldReturnArrayOfPlanetsWhenGettingAllPlanets() throws Exception {

        List<Planet> planets = new ArrayList<Planet>();
        Planet model = Planet.builder()
                .id("1")
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();
        planets.add(model);

        when(mockService.findAll()).thenReturn(planets);
        List<PlanetResponseModel> responses = planetsService.findAll();

        List<PlanetResponseModel> expected = new ArrayList<PlanetResponseModel>();
        for(Planet planet: planets){
            PlanetResponseModel response = planet._toConvertPlanetResponseModel();
            expected.add(response);
        }

        Assert.assertFalse(responses.isEmpty());
        Assert.assertEquals(expected, responses);
    }

    @Test
    public void TestShouldReturnEmptyArrayOfPlanetsWhenGettingAllPlanetsAndThereAreNoPlanets() throws Exception {

        List<Planet> planets = new ArrayList<Planet>();
        List<PlanetResponseModel> expected = new ArrayList<PlanetResponseModel>();

        when(mockService.findAll()).thenReturn(planets);
        List<PlanetResponseModel> responses = planetsService.findAll();

        Assert.assertTrue(responses.isEmpty());
        Assert.assertEquals(expected, responses);
    }

    @Test
    public void TestShouldCreateAnPlanet() throws Exception {

        PlanetRequestModel model = PlanetRequestModel
                .builder()
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();

        Planet result = model._toConvertPlanet();

        when(mockService.insert(result)).thenReturn(result);
        PlanetResponseModel response = planetsService.add(model);

        assertEquals(result._toConvertPlanetResponseModel(), response);
    }

    @Test
    public void TestShouldDeleteAnPlanet() throws Exception {

        String id = "1";
        Planet planet = Planet
                .builder()
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();

        when(mockService.findById(id)).thenReturn(Optional.ofNullable(planet));
        doNothing().when(mockService).delete(planet);
        planetsService.delete(id);
    }
}
