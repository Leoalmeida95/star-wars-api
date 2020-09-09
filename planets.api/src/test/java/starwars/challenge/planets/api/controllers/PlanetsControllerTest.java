package starwars.challenge.planets.api.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    private String URL;

    @Before
    public void setUp() {
        planetsController = new PlanetsController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(planetsController).build();
    }

    @Test
    public void testShouldReturnArrayOfPlanets() throws Exception {

        List<String> planets = new ArrayList<>();
        planets.add("Dagobah");
        planets.add("Estrela da Morte");
        planets.add("Endor");

        when(mockService.findAll()).thenReturn(planets);

        MockHttpServletResponse response = mockMvc.perform(
                get("/planets")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"Dagobah\",\"Estrela da Morte\",\"Endor\"]",
                response.getContentAsString());
    }

    @Test
    public void testShouldReturnAnPlanetWhenReceivAnId() throws Exception{

        when(mockService.findById(1)).thenReturn("Estrela da Morte");

        MockHttpServletResponse response = mockMvc.perform(
                get("/planets/1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Estrela da Morte", response.getContentAsString());
    }
}
