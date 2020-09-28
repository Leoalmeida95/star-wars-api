package starwars.challenge.planets.api.validation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starwars.challenge.planets.api.exceptions.StarWarsException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlanetsValidationTest {
    private MockMvc mockMvc;
    PlanetsValidation planetsValidation;

    @Before
    public void setUp() {
        planetsValidation = new PlanetsValidation();
        mockMvc = MockMvcBuilders.standaloneSetup(planetsValidation).build();
    }

    @Test
    public void TestShouldThrowExceptionWhenReceivEmptyOrNullPropertys() {

        String name = "name";
        String climate = "climate";
        String terrain = "terrain";
        String empty = "";

        assertThatThrownBy(() -> planetsValidation.verifyProperties(name,empty,empty))
                .isInstanceOf(StarWarsException.class);
        assertThatThrownBy(() -> planetsValidation.verifyProperties(empty,climate,empty))
                .isInstanceOf(StarWarsException.class);
        assertThatThrownBy(() -> planetsValidation.verifyProperties(empty,empty,terrain))
                .isInstanceOf(StarWarsException.class);
        assertThatThrownBy(() -> planetsValidation.verifyProperties(name,climate,null))
                .isInstanceOf(StarWarsException.class);
        assertThatThrownBy(() -> planetsValidation.verifyProperties(name,null,terrain))
                .isInstanceOf(StarWarsException.class);
        assertThatThrownBy(() -> planetsValidation.verifyProperties(null,climate,terrain))
                .isInstanceOf(StarWarsException.class);
    }
}
