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
import pl.kubakra.flywithus.flight.SearchFlightsCriteria;

import java.util.Set;

@RestController
public class SearchEndpoint {

    private final FlightRepo flightRepo;

    public SearchEndpoint(@Autowired FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    @GetMapping(value = "/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Flight> search(@RequestBody SearchFlightsCriteria searchFlightsCriteria) {
        Set<Flight> flights = flightRepo.getAll(searchFlightsCriteria);
        flights.forEach(f -> addHateoas(f));
        return flights;
    }

    @GetMapping(value = "/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> getFlight(@PathVariable String id) {
        return flightRepo.get(id).map(f -> ResponseEntity.ok().body(addHateoas(f))).orElse(ResponseEntity.notFound().build());
    }

    private Flight addHateoas(Flight f) {
        // every call will change 'db', so removeLinks it's a hack to make it work without real immutable objects (ResourceSupport) and real db
        f.removeLinks();
        f.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SearchEndpoint.class).getFlight(f.id().toString())).withSelfRel());
        return f;
    }

}