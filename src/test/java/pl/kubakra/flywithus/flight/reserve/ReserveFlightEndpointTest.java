package pl.kubakra.flywithus.flight.reserve;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import pl.kubakra.flywithus.FlyWithUsApp;

import java.nio.charset.Charset;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
@WebAppConfiguration
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
                        "\"paymentDeadline\":\"2018-02-26T15:00:00\"," +
                        "\"price\":{\"total\":505.35,\"discount\":0}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849/reservations/7c989afa-fbd6-4cfd-9e34-931b42c2a23b\"}" +
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
                        "\"paymentDeadline\":\"2018-02-26T15:00:00\"," +
                        "\"price\":{\"total\":555.35,\"discount\":0}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/486f1894-0297-4441-9341-1e1b7edb9849/reservations/aa0d891a-cde7-483d-95c5-7883e1d458c7\"}" +
                        "}" +
                        "}"));
    }

    @Test
    public void shouldMakeReservationForRegisteredUser() throws Exception {

        // when
        mockMvc.perform(post("/flights/4c3f5ebf-d9e8-4044-9e59-6b8a273a9914/reservations")
                .content("{" +
                        "\"quickCheckIn\":true," +
                        "\"user\":\"KubaKra\"" +
                        "}")
                .contentType(contentType))

                // then
                .andExpect(status().isCreated())
                .andExpect(content().json("{" +
                        "\"paymentDeadline\":\"2018-02-26T15:00:00\"," +
                        "\"price\":{\"total\":14003.45,\"discount\":5}," +
                        "\"_links\":{" +
                        "\"flight\":{\"href\":\"http://localhost/flights/4c3f5ebf-d9e8-4044-9e59-6b8a273a9914\"}," +
                        "\"self\":{\"href\":\"http://localhost/flights/4c3f5ebf-d9e8-4044-9e59-6b8a273a9914/reservations/2764618e-0448-4814-9105-813c1a919c76\"}" +
                        "}" +
                        "}"));

    }

    @Test
    public void shouldReturnBlaBlaWhenFlightIsNotAvailableAnymore() throws Exception {

    }

    @Test
    public void shouldReturnBlaBla2WhenUserSendNotExistingFlightData() throws Exception {

    }

    @Test
    public void shouldReturnBlaBla3WhenPriceChanged() throws Exception {

    }

}