package pl.kubakra.flywithus.flight.search;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
public class SearchEndpoint {

    @GetMapping(value = "/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Flight> search(@RequestBody SearchFlightsCriteria searchFlightsCriteria) {
        // TODO replace mock data with real repo call
        if (searchFlightsCriteria.isOneWayTicket()) {
            return oneWayTicket();
        }
        return twoWaysTicket();
    }

    private Set<Flight> oneWayTicket() {
        Flight flight = new OneWayFlight("Swiss", new Flight.Price(BigDecimal.valueOf(418), BigDecimal.valueOf(418)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)));
        return ImmutableSet.of(flight);
    }

    private Set<Flight> twoWaysTicket() {
        Flight flight = new TwoWaysFlight("LOT", new Flight.Price(BigDecimal.valueOf(130.5), BigDecimal.valueOf(391.5)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                        LocalDateTime.of(2018, 02, 27, 20, 40)));
        return ImmutableSet.of(flight);
    }

}