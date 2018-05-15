package pl.kubakra.flywithus.flight;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.kubakra.flywithus.flight.FlightRepo;
import pl.kubakra.flywithus.flight.TestFlightRepo;
import pl.kubakra.flywithus.flight.reserve.ReservationRepo;
import pl.kubakra.flywithus.flight.reserve.ReservationService;
import pl.kubakra.flywithus.tech.id.IdGenerator;
import pl.kubakra.flywithus.tech.time.TimeService;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class TestConfiguration {

    public static final LocalDateTime NOW = LocalDateTime.of(2018, 05, 14, 17, 17);
    public static final UUID UUID = java.util.UUID.fromString("486f1894-0297-4441-9341-1e1b7edb9849");


    @Bean
    public ReservationService reservationService() {
        return new ReservationService(timeService(), idGenerator(), reservationRepo());
    }

    @Bean
    public TimeService timeService() {
        return new TimeService() {
            @Override
            public LocalDateTime now() {
                return NOW;
            }

        };
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator() {
            @Override
            public UUID generate() {
                return UUID;
            }
        };
    }

    @Bean
    public ReservationRepo reservationRepo() {
        return new ReservationRepo();
    }


    @Bean
    @Primary
    public FlightRepo flightRepo() {
        return new TestFlightRepo();
    }

}