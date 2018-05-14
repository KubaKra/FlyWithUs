package pl.kubakra.flywithus.flight;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class TestFlightRepo extends FlightRepo {
//    Expected: 2018-02-26T15:00:00
//    got: 2018-05-16T17:17:00
//    ; price.total
//    Expected: 14003.45
//    got: 2669.5
    private static final Map<String, Flight> FLIGHTS = ImmutableMap.of(
            "4c3f5ebf-d9e8-4044-9e59-6b8a273a9914", new TwoWaysFlight(UUID.fromString("4c3f5ebf-d9e8-4044-9e59-6b8a273a9914"),
                    "Lufthansa", new Flight.Price(BigDecimal.valueOf(1405), BigDecimal.valueOf(2810)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                            LocalDateTime.of(2018, 02, 26, 13, 55)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                            LocalDateTime.of(2018, 02, 27, 20, 40))),
            "486f1894-0297-4441-9341-1e1b7edb9849", new TwoWaysFlight(UUID.fromString("486f1894-0297-4441-9341-1e1b7edb9849"),
                    "Air France", new Flight.Price(BigDecimal.valueOf(3280.20), BigDecimal.valueOf(16401)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                            LocalDateTime.of(2018, 02, 26, 13, 55)),
                    new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                            LocalDateTime.of(2018, 02, 27, 20, 40)))
    );

    @Override
    public Set<Flight> getAll(SearchFlightsCriteria criteria) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Flight> get(String id) {
        return Optional.ofNullable(FLIGHTS.get(id));
    }

}