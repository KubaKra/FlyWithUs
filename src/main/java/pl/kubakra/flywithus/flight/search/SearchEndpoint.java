package pl.kubakra.flywithus.flight.search;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
public class SearchEndpoint {

    @GetMapping(value = "/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Flight> search(@RequestBody SearchFlightsCriteria searchFlightsCriteria) {
        // TODO replace mock data with real repo call
        if (searchFlightsCriteria.isOneWayTicket()) {
            return ImmutableSet.of(oneWayTicket());
        }
        return ImmutableSet.of(twoWaysTicket());
    }

    @GetMapping(value = "/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flight getFlight(@PathVariable String id) {
        // TODO replace mock data with real repo call
        return oneWayTicket();
    }

    private Flight oneWayTicket() {
        Flight flight = new OneWayFlight(UUID.fromString("ee18ccca-078d-4a62-95a7-2aea11d5ddcb"), "Swiss", new Flight.Price(BigDecimal.valueOf(418), BigDecimal.valueOf(418)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)));
        flight.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(flight.id().toString())).withSelfRel());
        return flight;
    }

    private Flight twoWaysTicket() {
        Flight flight = new TwoWaysFlight(UUID.fromString("41f8b9f3-388d-4ddc-822e-233c383fa371"), "LOT", new Flight.Price(BigDecimal.valueOf(130.5), BigDecimal.valueOf(391.5)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                        LocalDateTime.of(2018, 02, 27, 20, 40)));
        flight.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(flight.id().toString())).withSelfRel());
        return flight;
    }

}