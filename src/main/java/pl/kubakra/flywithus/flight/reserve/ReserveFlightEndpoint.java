package pl.kubakra.flywithus.flight.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.FlightRepo;
import pl.kubakra.flywithus.flight.search.SearchEndpoint;
import pl.kubakra.flywithus.tech.serialization.LocalDateTimeSerializer;
import pl.kubakra.flywithus.user.User;
import pl.kubakra.flywithus.user.UserRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public ResponseEntity<ReservationDTO> reserve(@PathVariable String id, @RequestBody ReservationRequest reservationRequest) {
        Optional<Flight> flight = flightRepo.getBy(UUID.fromString(id));
        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepo.getUser(reservationRequest.getUser());
        Reservation reservation = reservationService
                .reserve(flight.get())
                .withPeopleCount(reservationRequest.getPeopleCount())
                .withQuickCheckIn(reservationRequest.isQuickCheckIn())
                .by(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createDTO(reservation));
    }

    @GetMapping(value = "/flights/reservations/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable String reservationId) {
        return reservationRepo.getBy(UUID.fromString(reservationId))
                .map(re -> ResponseEntity.ok().body(createDTO(re)))
                .orElse(ResponseEntity.notFound().build());
    }

    private ReservationDTO createDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO(reservation.paymentDeadline(), reservation.price().total());
        reservationDTO.add(linkTo(
                methodOn(SearchEndpoint.class).getFlight(reservation.flightId().toString())).withRel("flight"));
        reservationDTO.add(linkTo(
                methodOn(ReserveFlightEndpoint.class).getReservation(reservation.id().toString())).withSelfRel());
        reservationDTO.add(new Link(reservation.payment().map(p -> p.link()).orElse("not available"), "payment"));
        return reservationDTO;
    }

    public static class ReservationDTO extends ResourceSupport {

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private final LocalDateTime paymentDeadline;
        @JsonProperty
        private final BigDecimal totalPrice;

        public ReservationDTO(LocalDateTime paymentDeadline, BigDecimal totalPrice) {
            this.paymentDeadline = paymentDeadline;
            this.totalPrice = totalPrice;
        }

    }


}