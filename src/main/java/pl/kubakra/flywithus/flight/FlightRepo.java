package pl.kubakra.flywithus.flight;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

// it's naive de-normalized cache representation, my purpose is not to implement real search;
@Repository
public class FlightRepo {

    private static final String ONE_WAY_TICKET_KEY = "ee18ccca-078d-4a62-95a7-2aea11d5ddcb";
    private static final String TWO_WAYS_TICKET_KEY = "41f8b9f3-388d-4ddc-822e-233c383fa371";
    private static final Map<String, Flight> FLIGHTS = ImmutableMap.of(
            ONE_WAY_TICKET_KEY, new OneWayFlight(UUID.fromString(ONE_WAY_TICKET_KEY), "Swiss", new Flight.Price(BigDecimal.valueOf(418), BigDecimal.valueOf(418)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                            LocalDateTime.of(2018, 02, 26, 13, 55))),
            TWO_WAYS_TICKET_KEY, new TwoWaysFlight(UUID.fromString(TWO_WAYS_TICKET_KEY), "LOT", new Flight.Price(BigDecimal.valueOf(130.5), BigDecimal.valueOf(391.5)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                            LocalDateTime.of(2018, 02, 26, 13, 55)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                            LocalDateTime.of(2018, 02, 27, 20, 40)))
            );

    public Set<Flight> getAll(GetFlightsCriteria criteria) {
        if (criteria.isOneWayTicket()) {
            return ImmutableSet.of(FLIGHTS.get(ONE_WAY_TICKET_KEY));
        }
        return ImmutableSet.of(FLIGHTS.get(TWO_WAYS_TICKET_KEY));
    }

    public Optional<Flight> get(String id) {
        return Optional.ofNullable(FLIGHTS.get(id));
    }

}