package pl.kubakra.flywithus.flight.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.kubakra.flywithus.FlyWithUsApp;
import pl.kubakra.flywithus.flight.reserve.ReservationServiceTestConfiguration;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class SearchEndpointTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void validSearchCriteriaForReturnFlightShouldReturnAllPossibleFlights() throws Exception {

        // when
        mockMvc.perform(get("/flights")
                .content("{\n" +
                        "    \"from\": \"Warsaw\",\n" +
                        "    \"to\": \"Katowice\",\n" +
                        "    \"when\": \"" + ReservationServiceTestConfiguration.NOW.plusDays(1).toLocalDate().toString() + "\",\n" +
                        "    \"returnDate\": \"" + ReservationServiceTestConfiguration.NOW.plusDays(2).toLocalDate().toString() + "\",\n" +
                        "    \"peopleCount\": 3\n" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"type\":\"twoWays\"," +
                        "\"company\":\"LOT\"," +
                        "\"price\":{\"perPerson\":130.5,\"total\":391.5}," +
                        "\"toDuration\":{\"departure\":\"2018-02-26T13:20:00\",\"arrival\":\"2018-02-26T13:55:00\"}," +
                        "\"returnDuration\":{\"departure\":\"2018-02-27T20:10:00\",\"arrival\":\"2018-02-27T20:40:00\"}," +
                        "\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/flights/41f8b9f3-388d-4ddc-822e-233c383fa371\"}]" +
                        "}]"));
    }

    @Test
    public void validSearchCriteriaForOneWayFlightShouldReturnAllPossibleFlights() throws Exception {

        // when
        mockMvc.perform(get("/flights")
                .content("{\n" +
                        "    \"from\": \"Warsaw\",\n" +
                        "    \"to\": \"Zurich\",\n" +
                        "    \"when\": \"" + ReservationServiceTestConfiguration.NOW.plusDays(1).toLocalDate().toString() + "\",\n" +
                        "    \"peopleCount\": 1\n" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"type\":\"oneWay\"," +
                        "\"company\":\"Swiss\"," +
                        "\"price\":{\"perPerson\":418,\"total\":418}," +
                        "\"duration\":{\"departure\":\"2018-02-26T13:20:00\",\"arrival\":\"2018-02-26T13:55:00\"}," +
                        "\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/flights/ee18ccca-078d-4a62-95a7-2aea11d5ddcb\"}]" +
                        "}]"));
    }

    @Test
    public void searchDatesShouldBeInFuture() throws Exception {

        // when
        mockMvc.perform(get("/flights")
                .content("{\n" +
                        "    \"from\": \"Warsaw\",\n" +
                        "    \"to\": \"Zurich\",\n" +
                        "    \"when\": \"" + ReservationServiceTestConfiguration.NOW.minusDays(1).toLocalDate().toString() + "\",\n" +
                        "    \"peopleCount\": 1\n" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isBadRequest());
    }

}