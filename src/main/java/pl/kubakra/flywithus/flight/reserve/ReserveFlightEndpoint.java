package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kubakra.flywithus.flight.search.SearchEndpoint;
import pl.kubakra.flywithus.user.User;
import pl.kubakra.flywithus.user.UserRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class ReserveFlightEndpoint {

    private final UserRepo userRepo;

    public ReserveFlightEndpoint(@Autowired UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/flights/{id}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> reserve(@PathVariable String id, @RequestBody ReservationRequest reservationRequest) {
        // TODO replace mock data with real repo call

        User user = userRepo.getUser(reservationRequest.getUser());

        if (user.isRegistered()) {
            Reservation reservation = registeredUsersReservation(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        }

        if (reservationRequest.isQuickCheckIn()) {
            Reservation reservation = quickCheckInReservation(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        }

        Reservation reservation = reservation(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    private Reservation registeredUsersReservation(String id) {
        Reservation reservation = new Reservation(UUID.fromString("2764618e-0448-4814-9105-813c1a919c76"), LocalDateTime.of(2018, 02, 26, 15, 00),
                new Reservation.Price(BigDecimal.valueOf(14003.45), BigDecimal.valueOf(5)));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(id)).withRel("flight"));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReserveFlightEndpoint.class).getReservation(id, reservation.id().toString())).withSelfRel());
        return reservation;

    }

    private Reservation quickCheckInReservation(String id) {
        Reservation reservation = new Reservation(UUID.fromString("aa0d891a-cde7-483d-95c5-7883e1d458c7"), LocalDateTime.of(2018, 02, 26, 15, 00),
                new Reservation.Price(BigDecimal.valueOf(555.35), BigDecimal.ZERO));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(id)).withRel("flight"));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReserveFlightEndpoint.class).getReservation(id, reservation.id().toString())).withSelfRel());
        return reservation;
    }

    private Reservation reservation(String id) {
        Reservation reservation = new Reservation(UUID.fromString("7c989afa-fbd6-4cfd-9e34-931b42c2a23b"), LocalDateTime.of(2018, 02, 26, 15, 00),
                new Reservation.Price(BigDecimal.valueOf(505.35), BigDecimal.ZERO));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(id)).withRel("flight"));
        reservation.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReserveFlightEndpoint.class).getReservation(id, reservation.id().toString())).withSelfRel());
        return reservation;
    }

    @GetMapping(value = "/flights/{flightId}/reservations/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable String flightId, @PathVariable String reservationId) {
        // TODO replace mock data with real repo call
        return quickCheckInReservation(flightId);
    }


}