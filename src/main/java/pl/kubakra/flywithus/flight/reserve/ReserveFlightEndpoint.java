package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.FlightRepo;
import pl.kubakra.flywithus.flight.search.SearchEndpoint;
import pl.kubakra.flywithus.user.User;
import pl.kubakra.flywithus.user.UserRepo;

import java.util.Optional;

@RestController
public class ReserveFlightEndpoint {

    private final FlightRepo flightRepo;
    private final UserRepo userRepo;
    private final ReservationService reservationService;
    private final ReservationRepo reservationRepo;

    public ReserveFlightEndpoint(@Autowired FlightRepo flightRepo, @Autowired UserRepo userRepo,
                                 @Autowired ReservationService reservationService, @Autowired ReservationRepo reservationRepo) {
        this.flightRepo = flightRepo;
        this.userRepo = userRepo;
        this.reservationService = reservationService;
        this.reservationRepo = reservationRepo;
    }

    @PostMapping(value = "/flights/{id}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> reserve(@PathVariable String id, @RequestBody ReservationRequest reservationRequest) {
        Optional<Flight> flight = flightRepo.get(id);
        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepo.getUser(reservationRequest.getUser());
        Reservation reservation = reservationService.reserve(flight.get()).by(user);

        reservation = addHateoas(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }


    @GetMapping(value = "/flights/reservations/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable String reservationId) {
        Reservation reservation = reservationRepo.getBy(reservationId);
        reservation = addHateoas(reservation);
        return reservation;
    }

    private Reservation addHateoas(Reservation r) {
        // every call will change 'db', so removeLinks it's a hack to make it work without real immutable objects (ResourceSupport) and real db
        r.removeLinks();
        r.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(r.flightId().toString())).withRel("flight"));
        r.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReserveFlightEndpoint.class).getReservation(r.id().toString())).withSelfRel());
        return r;
    }


}