package pl.kubakra.flywithus.flight.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.FlightRepo;
import pl.kubakra.flywithus.flight.GetFlightsCriteria;
import pl.kubakra.flywithus.tech.time.TimeService;

import java.util.Set;
import java.util.UUID;

@RestController
public class SearchEndpoint {

    private final FlightRepo flightRepo;
    private final TimeService timeService;

    public SearchEndpoint(@Autowired FlightRepo flightRepo, @Autowired TimeService timeService) {
        this.flightRepo = flightRepo;
        this.timeService = timeService;
    }

    @GetMapping(value = "/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Flight>> search(@RequestBody GetFlightsCriteria getFlightsCriteria) {

        if (!getFlightsCriteria.areDatesAfterThan(timeService.now().toLocalDate())) {
            return ResponseEntity.badRequest().build();
        }

        Set<Flight> flights = flightRepo.getAll(getFlightsCriteria);
        flights.forEach(f -> addHateoas(f));
        return ResponseEntity.ok(flights);
    }

    @GetMapping(value = "/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> getFlight(@PathVariable String id) {
        return flightRepo.getBy(UUID.fromString(id)).map(f -> ResponseEntity.ok().body(addHateoas(f))).orElse(ResponseEntity.notFound().build());
    }

    private Flight addHateoas(Flight f) {
        // every call will change 'db',
        // so removeLinks it's a quick hack to make it work without real immutable objects (ResourceSupport) and real db.
        // normally i'd use composition (create new object that contains Flight and has links)
        f.removeLinks();
        f.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(f.id().toString())).withSelfRel());
        return f;
    }

}