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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class PlanetsControllerTest {

    private MockMvc mockMvc;
    PlanetsController planetsController;
    private String URL;

    @Before
    public void setUp() {
        URL = "/planets";
        planetsController = new PlanetsController();
        mockMvc = MockMvcBuilders.standaloneSetup(planetsController).build();
    }

    @Test
    public void testSuccessFind() throws Exception{

        MockHttpServletResponse response = mockMvc.perform(
                get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Hello World",
                response.getContentAsString());
    }

    @Test
    public void testNotFoundFind() throws Exception{
        MockHttpServletResponse response = mockMvc.perform(
                get(URL + "x")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertNotEquals("Hello", response.getContentAsString());
    }
}
