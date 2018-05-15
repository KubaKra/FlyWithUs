package pl.kubakra.flywithus.flight.reserve;

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

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class ReserveFlightEndpointTest {

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
    public void shouldMakeReservation() throws Exception {

        // when
        mockMvc.perform(post("/flights/486f1894-0297-4441-9341-1e1b7edb9849/reservations")
                .content("{\"quickCheckIn\":false}")
                .contentType(contentType))

                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"paymentDeadline\":\"2018-05-16T17:17:00\"," +
                        "\"price\":{\"total\":16401,\"discount\":0}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/reservations/" + ReservationTestContext.UUID + "\"}" +
                        "}" +
                        "}"));
    }

    @Test
    public void shouldMakeReservationWithQuickCheckIn() throws Exception {

        // when
        mockMvc.perform(post("/flights/486f1894-0297-4441-9341-1e1b7edb9849/reservations")
                .content("{\"quickCheckIn\":true}")
                .contentType(contentType))

                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"paymentDeadline\":\"2018-05-16T17:17:00\"," +
                        "\"price\":{\"total\":16401,\"discount\":0}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/reservations/" + ReservationTestContext.UUID + "\"}" +
                        "}" +
                        "}"));
    }

    @Test
    public void shouldMakeReservationForRegisteredUser() throws Exception {

        // when
        mockMvc.perform(post("/flights/486f1894-0297-4441-9341-1e1b7edb9849/reservations")
                .content("{" +
                        "\"quickCheckIn\":true," +
                        "\"user\":\"KubaKra\"" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"paymentDeadline\":\"2018-05-16T17:17:00\"," +
                        "\"price\":{\"total\":15580.95,\"discount\":5}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/reservations/" + ReservationTestContext.UUID + "\"}" +
                        "}" +
                        "}"));

    }

    @Test
    public void shouldReturn404WhenFlightIsNotAvailable() throws Exception {

        // when
        mockMvc.perform(post("/flights/not-existing-flight/reservations")
                .content("{" +
                        "\"quickCheckIn\":true," +
                        "\"user\":\"KubaKra\"" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isNotFound());
    }

}