package starwars.challenge.planets.api.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starwars.challenge.planets.api.domains.Planet;
import starwars.challenge.planets.api.domains.PlanetRequestModel;
import starwars.challenge.planets.api.domains.PlanetResponseModel;
import starwars.challenge.planets.api.repositorys.PlanetsRepository;
import starwars.challenge.planets.api.validations.PlanetsValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PlanetsServiceTest {

    private MockMvc mockMvc;
    PlanetsService planetsService;
    PlanetsRepository mockService = mock(PlanetsRepository.class);
    PlanetsValidation validationMockService = mock(PlanetsValidation.class);

    @Before
    public void setUp() {
        planetsService = new PlanetsService(mockService,validationMockService);
        mockMvc = MockMvcBuilders.standaloneSetup(planetsService).build();
    }

    @Test
    public void TestShouldReturnArrayOfPlanetsWhenGettingAllPlanets() {

        List<Planet> planets = new ArrayList<>();
        Planet planet_response = getPlanet();
        planets.add(planet_response);

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
    public void TestShouldReturnEmptyArrayOfPlanetsWhenGettingAllPlanetsAndThereAreNoPlanets() {

        List<Planet> planets = new ArrayList<Planet>();
        List<PlanetResponseModel> expected = new ArrayList<PlanetResponseModel>();

        when(mockService.findAll()).thenReturn(planets);
        List<PlanetResponseModel> responses = planetsService.findAll();

        Assert.assertTrue(responses.isEmpty());
        Assert.assertEquals(expected, responses);
    }

    @Test
    public void TestShouldCreateAnPlanet() {

        PlanetRequestModel model = PlanetRequestModel
                .builder()
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();

        Planet result = model._toConvertPlanet();

        verify(validationMockService, never()).verifyProperties(result.getName(),result.getClimate(), result.getTerrain());
        //doNothing().when(validationMockService).verifyProperties(result.getName(),result.getClimate(), result.getTerrain());
        when(mockService.insert(result)).thenReturn(result);
        PlanetResponseModel response = planetsService.add(model);

        assertEquals(result._toConvertPlanetResponseModel(), response);
    }

    @Test
    public void TestShouldDeleteAnPlanet() {

        String id = "5f70cd13614e827cf8d00fb3";
        Planet planet = getPlanet();

        when(mockService.findById(id)).thenReturn(Optional.ofNullable(planet));
        doNothing().when(mockService).delete(planet);
        planetsService.delete(id);
    }

    @Test
    public void TestShouldReturnAnPlanetWhenReceivAnId() {

        String id = "5f70cd13614e827cf8d00fb3";
        Planet planet = getPlanet();

        when(mockService.findById(id)).thenReturn(Optional.ofNullable(planet));
        PlanetResponseModel response = planetsService.findById(id);

        Assert.assertEquals(planet._toConvertPlanetResponseModel(), response);
    }

    @Test
    public void TestShouldReturnAnArrayOfPlanetsWhenReceivAnName() {

        String name = "Dagobah";
        List<Planet> planets = new ArrayList<>();
        Planet planet_response = getPlanet();
        planets.add(planet_response);
        Optional<ArrayList<Planet>> result = Optional.of((ArrayList<Planet>) planets);

        when(mockService.findByName(name)).thenReturn(result);
        List<PlanetResponseModel> responses = planetsService.findByName(name);

        List<PlanetResponseModel> expected = new ArrayList<PlanetResponseModel>();
        for(Planet planet: planets){
            PlanetResponseModel response = planet._toConvertPlanetResponseModel();
            expected.add(response);
        }

        Assert.assertFalse(responses.isEmpty());
        Assert.assertEquals(expected, responses);
    }

    private Planet getPlanet(){
        return Planet
                .builder()
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();
    }
}
