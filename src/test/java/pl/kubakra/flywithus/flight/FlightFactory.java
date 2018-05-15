package pl.kubakra.flywithus.flight;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Service
public class FlightFactory {

    private final FlightRepo flightRepo;

    public FlightFactory(@Autowired FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }


    public Flight create(boolean flightIsBeforeNow) {
        Flight flight = Mockito.mock(Flight.class);
        UUID flightId = UUID.randomUUID();
        given(flight.id()).willReturn(flightId);
        given(flight.areDatesBefore(any(LocalDateTime.class))).willReturn(flightIsBeforeNow);
        flightRepo.save(flight);
        return flight;
    }

    public Flight oneWayFlight() {
        Flight flight = new OneWayFlight(UUID.fromString("ee18ccca-078d-4a62-95a7-2aea11d5ddcb"), "Swiss", new Flight.Price(BigDecimal.valueOf(418), BigDecimal.valueOf(418)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)));
        flightRepo.save(flight);
        return flight;
    }

    public Flight twoWaysFlight() {
        Flight flight = new TwoWaysFlight(UUID.fromString("41f8b9f3-388d-4ddc-822e-233c383fa371"), "LOT", new Flight.Price(BigDecimal.valueOf(130.5), BigDecimal.valueOf(391.5)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                        LocalDateTime.of(2018, 02, 27, 20, 40)));
        flightRepo.save(flight);
        return flight;
    }

    public Flight createAirFranceFlight() {
        Flight flight = new TwoWaysFlight(UUID.fromString("486f1894-0297-4441-9341-1e1b7edb9849"),
                "Air France", new Flight.Price(BigDecimal.valueOf(3280.20), BigDecimal.valueOf(16401)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                        LocalDateTime.of(2018, 02, 27, 20, 40)));
        flightRepo.save(flight);
        return flight;
    }

    public Flight createLufthansaFlight() {
        Flight flight = new TwoWaysFlight(UUID.fromString("4c3f5ebf-d9e8-4044-9e59-6b8a273a9914"),
                "Lufthansa", new Flight.Price(BigDecimal.valueOf(1405), BigDecimal.valueOf(2810)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 26, 13, 20),
                        LocalDateTime.of(2018, 02, 26, 13, 55)),
                new Flight.Duration(LocalDateTime.of(2018, 02, 27, 20, 10),
                        LocalDateTime.of(2018, 02, 27, 20, 40)));
        flightRepo.save(flight);
        return flight;
    }

}