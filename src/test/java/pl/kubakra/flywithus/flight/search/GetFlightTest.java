package pl.kubakra.flywithus.flight.search;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class GetFlightTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnExistingFlight() throws Exception {

        // when
        mockMvc.perform(get("/flights/ee18ccca-078d-4a62-95a7-2aea11d5ddcb"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"type\":\"oneWay\"," +
                        "\"company\":\"Swiss\"," +
                        "\"price\":{\"perPerson\":418,\"total\":418}," +
                        "\"duration\":{\"departure\":\"2018-02-26T13:20:00\",\"arrival\":\"2018-02-26T13:55:00\"}," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/flights/ee18ccca-078d-4a62-95a7-2aea11d5ddcb\"}}" +
                        "}"));
    }

    @Test
    public void shouldReturn404ForNotExistingFlight() throws Exception {

        // when
        mockMvc.perform(get("/flights/hy-hy-hy"))

                // then
                .andExpect(status().isNotFound());
    }

}