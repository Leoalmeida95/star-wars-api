package starwars.challenge.planets.api.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starwars.challenge.planets.api.exceptions.StarWarsException;
import starwars.challenge.planets.api.services.PlanetsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    public void testShouldReturnArrayOfPlanetsWhenGettingAllPlanets() throws Exception {

        List<String> planets = new ArrayList<>();
        planets.add("Dagobah");
        planets.add("Estrela da Morte");
        planets.add("Endor");

        when(mockService.findAll()).thenReturn(planets);

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"Dagobah\",\"Estrela da Morte\",\"Endor\"]",
                response.getContentAsString());
    }

    @Test
    public void testShouldReturnNotFoundErrorWhenGettingPlanetsAndThereAreNoPlanetsYet() throws Exception{

        when(mockService.findAll()).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void testShouldReturnInternalServerErrorWhenGettingPlanetsAndOcurredInternalError() throws Exception{

        when(mockService.findAll()).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void testShouldReturnBadRequestErrorWhenGettingPlanetsWithInvalidCredentials() throws Exception{

        when(mockService.findAll()).thenThrow(
                new StarWarsException(HttpStatus.BAD_REQUEST.value(),
                        "Error getting all planets",
                        HttpStatus.BAD_REQUEST)
        );

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testShouldReturnAnPlanetWhenReceivAnId() throws Exception{

        Integer id = 1;
        when(mockService.findById(id)).thenReturn("Estrela da Morte");

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Estrela da Morte", response.getContentAsString());
    }

    @Test
    public void testShouldReturnNotFoundErrorWhenReceivAnIdNonexistent() throws Exception{

        Integer id = 1;
        when(mockService.findById(id)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets/"+id)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void testShouldReturnInternalServerErrorWhenReceivAnIdAndOcurredInternalError() throws Exception{

        Integer id = 0;
        when(mockService.findById(id)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets/"+id)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void testShouldReturnBadRequestErrorWhenReceivAnInvalidId() throws Exception{

        Integer id = 000;
        when(mockService.findById(id)).thenThrow(
                new StarWarsException(HttpStatus.BAD_REQUEST.value(),
                        "Error getting planet by id",
                        HttpStatus.BAD_REQUEST)
        );

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets/"+id)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testShouldReturnAnPlanetWhenReceivAnName() throws Exception {

        String name = "teste";
        when(mockService.findByName(name)).thenReturn("Estrela da Morte");

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets?name="+name)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Estrela da Morte", response.getContentAsString());
    }

    @Test
    public void testShouldReturnNotFoundErrorWhenReceivAnNameNonexistent() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets?name="+name)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(0, response.getContentLength());
    }

    @Test
    public void testShouldReturnInternalServerErrorWhenReceivAnNameAndOcurredInternalError() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets?name="+name)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void testShouldReturnBadRequestErrorWhenReceivAnInvalidName() throws Exception{

        String name = "teste";
        when(mockService.findByName(name)).thenThrow(
                new StarWarsException(HttpStatus.BAD_REQUEST.value(),
                        "Error getting planet by name",
                        HttpStatus.BAD_REQUEST)
        );

        MockHttpServletResponse response = mockMvc.perform(
                get(API + "/planets?name="+name)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}