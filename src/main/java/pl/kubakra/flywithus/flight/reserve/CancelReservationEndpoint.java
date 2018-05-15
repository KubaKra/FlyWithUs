package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.FlightRepo;
import pl.kubakra.flywithus.tech.time.TimeService;

import java.util.Optional;
import java.util.UUID;

@RestController
public class CancelReservationEndpoint {

    private final FlightRepo flightRepo;
    private final ReservationRepo reservationRepo;
    private final TimeService timeService;

    public CancelReservationEndpoint(@Autowired FlightRepo flightRepo, @Autowired ReservationRepo reservationRepo, @Autowired TimeService timeService) {
        this.flightRepo = flightRepo;
        this.reservationRepo = reservationRepo;
        this.timeService = timeService;
    }

    @DeleteMapping(value = "/flights/{flightId}/reservations/{reservationId}")
    public ResponseEntity<Void> reserve(@PathVariable String flightId, @PathVariable String reservationId) {

        Optional<Flight> flight = flightRepo.getBy(UUID.fromString(flightId));
        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Reservation> reservation = reservationRepo.getBy(UUID.fromString(reservationId));
        if (!reservation.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!flight.get().areDatesBefore(timeService.now().minusDays(5))) {
            return ResponseEntity.badRequest().build();
        }

        reservationRepo.delete(reservation.get());
        return ResponseEntity.noContent().build();
    }

}
