package starwars.challenge.planets.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starwars.challenge.planets.api.domain.Planet;
import starwars.challenge.planets.api.domain.PlanetRequestModel;
import starwars.challenge.planets.api.domain.PlanetResponseModel;
import starwars.challenge.planets.api.exceptions.StarWarsException;
import starwars.challenge.planets.api.services.PlanetsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class PlanetsControllerTest {

    private MockMvc mockMvc;
    PlanetsController planetsController;
    PlanetsService mockService = mock(PlanetsService.class);
    String API;

    @Before
    public void setUp() {
        API = "/api";
        planetsController = new PlanetsController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(planetsController).build();
    }

    @Test
    public void TestShouldReturnArrayOfPlanetsWhenGettingAllPlanets() throws Exception {

        List<PlanetResponseModel> planets = new ArrayList<>();
        PlanetResponseModel model = getPlanetResponseModel();
        planets.add(model);

        when(mockService.findAll()).thenReturn(planets);

        MockHttpServletResponse response = performMockHttpGet("/planets");

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"id\":\"1\",\"name\":\"Dagobah\",\"climate\":\"stormy\",\"terrain\":\"ground\"}]",
                response.getContentAsString());
    }

    @Test
    public void TestShouldReturnAnPlanetWhenReceivAnId() throws Exception{

        String id = "1";
        PlanetResponseModel model = getPlanetResponseModel();
        when(mockService.findById(id)).thenReturn(model);

        MockHttpServletResponse response = performMockHttpGet("/planets/" + id);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"id\":\"1\",\"name\":\"Dagobah\",\"climate\":\"stormy\",\"terrain\":\"ground\"}",
                response.getContentAsString());
    }

    @Test
    public void TestShouldReturnAnPlanetWhenReceivAnName() throws Exception {

        String name = "teste";
        List<PlanetResponseModel> planets = new ArrayList<>();
        PlanetResponseModel model = getPlanetResponseModel();
        planets.add(model);
        when(mockService.findByName(name)).thenReturn(planets);

        MockHttpServletResponse response = performMockHttpGet("/planets?name="+name);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"id\":\"1\",\"name\":\"Dagobah\",\"climate\":\"stormy\",\"terrain\":\"ground\"}]",
                response.getContentAsString());
    }

    @Test
    public void TestShouldDeleteAnPlanetWhenReceivAnId() throws Exception {

        String id = "5f70cd13614e827cf8d00fb3";

        MockHttpServletResponse response = performMockHttpDelete("/planets/"+id);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void TestShouldCreateAnPlanet() throws Exception {

        PlanetRequestModel model = getPlanetRequestModel();
        Planet result = model._toConvertPlanet();

        when(mockService.add(model)).thenReturn(result._toConvertPlanetResponseModel());

        MockHttpServletResponse response = performMockHttpPost("/planets", model);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"id\":null,\"name\":\"Dagobah\",\"climate\":\"stormy\",\"terrain\":\"ground\"}",
                response.getContentAsString());
    }

    @Test
    public void TestShouldReturnInternalServerErrorWhenGettingAllPlanetsAndOcurredInternalError() throws Exception{

        when(mockService.findAll()).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = performMockHttpGet("/planets");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnInternalServerErrorWhenReceivAnIdAndOcurredInternalError() throws Exception{

        String id = "5f70cd13614e827cf8d00fb3";
        when(mockService.findById(id)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = performMockHttpGet("/planets/"+id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnInternalServerErrorWhenReceivAnNameAndOcurredInternalError() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = performMockHttpGet("/planets?name="+name);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnInternalServerErrorWhenReceivAnIdToDeleteAndOcurredInternalError() throws Exception{

        String id = "1";
        NullPointerException ex = new NullPointerException("Some Error");
        doThrow(ex).when(mockService).delete(id);

        MockHttpServletResponse response = performMockHttpDelete("/planets/"+id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains(ex.getLocalizedMessage()));
    }

    @Test
    public void TestShouldReturnInternalServerErrorWhenReceivAnPlanetToCreateAndOcurredInternalError() throws Exception{

        PlanetRequestModel model = PlanetRequestModel
                                    .builder()
                                    .build();
        when(mockService.add(model)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = performMockHttpPost("/planets", model);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnBadRequestErrorWhenGettingAllPlanetsWithInvalidCredentials() throws Exception{

        when(mockService.findAll()).thenThrow(getStarWarsException("Error getting all planets",
                                                HttpStatus.BAD_REQUEST));

        MockHttpServletResponse response = performMockHttpGet("/planets");

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnBadRequestErrorWhenReceivAnInvalidId() throws Exception{

        String id = "000";
        when(mockService.findById(id)).thenThrow(
                getStarWarsException("Error getting planet by id",
                        HttpStatus.BAD_REQUEST));

        MockHttpServletResponse response = performMockHttpGet("/planets/"+id);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnBadRequestErrorWhenReceivAnInvalidName() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenThrow(
                getStarWarsException("Error getting planet by name",
                        HttpStatus.BAD_REQUEST));

        MockHttpServletResponse response = performMockHttpGet("/planets?name="+name);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void TestShouldReturnBadRequestErrorWhenReceivAnInvalidIdToDelete() throws Exception{

        String id = "1";
        StarWarsException ex = getStarWarsException("Error deleting an planet by id",
                HttpStatus.BAD_REQUEST);

        doThrow(ex).when(mockService).delete(id);

        MockHttpServletResponse response = performMockHttpDelete("/planets/"+id);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"statuscode\":\"400 BAD_REQUEST\",\"code\":\"400\",\"message\":\"Error deleting an planet by id\",\"status\":\"Error\"}",
                response.getContentAsString());
    }

    @Test
    public void TestShouldReturnBadRequestErrorWhenReceivAnInvalidPlanetToCreate() throws Exception{

        PlanetRequestModel model = PlanetRequestModel
                                    .builder()
                                    .build();
        StarWarsException ex = getStarWarsException("Error creating an planet",
                HttpStatus.BAD_REQUEST);

        when(mockService.add(model)).thenThrow(ex);

        MockHttpServletResponse response = performMockHttpPost("/planets", model);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"statuscode\":\"400 BAD_REQUEST\",\"code\":\"400\",\"message\":\"Error creating an planet\",\"status\":\"Error\"}",
                response.getContentAsString());
    }

    @Test
    public void TestShouldReturnNotFoundErrorWhenGettingAllPlanetsAndThereAreNoPlanetsYet() throws Exception{

        when(mockService.findAll()).thenReturn(null);

        MockHttpServletResponse response = performMockHttpGet("/planets");

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void TestShouldReturnNotFoundErrorWhenReceivAnIdNonexistent() throws Exception{

        String id = "5f70cd13614e827cf8d00fb3";
        when(mockService.findById(id)).thenReturn(null);

        MockHttpServletResponse response = performMockHttpGet("/planets/"+id);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void TestShouldReturnNotFoundErrorWhenReceivAnNameNonexistent() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenReturn(null);

        MockHttpServletResponse response = performMockHttpGet("/planets?name="+name);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void TestShouldReturnNotFoundErrorWhenReceivAnIdNonexistentToDelete() throws Exception{

        String id = "1";
        StarWarsException ex = getStarWarsException("This planet not exists",
                HttpStatus.NOT_FOUND);

        doThrow(ex).when(mockService).delete(id);

        MockHttpServletResponse response = performMockHttpDelete("/planets/"+id);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("{\"statuscode\":\"404 NOT_FOUND\",\"code\":\"404\",\"message\":\"This planet not exists\",\"status\":\"Error\"}",
                response.getContentAsString());
    }

    private MockHttpServletResponse performMockHttpDelete(String url) throws Exception {
        return mockMvc.perform(
                delete(API + url)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
    }

    private MockHttpServletResponse performMockHttpGet(String url) throws Exception {
        return mockMvc.perform(
                get(API + url)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
    }

    private MockHttpServletResponse performMockHttpPost(String url, PlanetRequestModel content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(content);
        return mockMvc.perform(
                post(API + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andReturn().getResponse();
    }

    private PlanetResponseModel getPlanetResponseModel(){
        return PlanetResponseModel.builder()
                .id("1")
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();
    }

    private PlanetRequestModel getPlanetRequestModel(){
        return PlanetRequestModel
                .builder()
                .name("Dagobah")
                .climate("stormy")
                .terrain("ground")
                .build();
    }

    private StarWarsException getStarWarsException(String message, HttpStatus status){
        return new StarWarsException(
                status.value(),
                message,
                status);
    }
}