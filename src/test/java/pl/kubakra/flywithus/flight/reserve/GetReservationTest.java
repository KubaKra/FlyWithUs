package pl.kubakra.flywithus.flight.reserve;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.kubakra.flywithus.FlyWithUsApp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class GetReservationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnExistingFlight() throws Exception {
        // TODO
    }

    @Test
    public void shouldReturn404ForNotExistingFlight() throws Exception {

        // when
        mockMvc.perform(get("/flights/santa/reservations/ee18ccca-078d-4a62-95a7-2aea11d5ddcb"))

                // then
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404ForNotExistingReservation() throws Exception {

        // when
        mockMvc.perform(get("/flights/ee18ccca-078d-4a62-95a7-2aea11d5ddcb/reservations/ho-ho-ho"))

                // then
                .andExpect(status().isNotFound());
    }


}