package pl.kubakra.flywithus.flight.reserve;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kubakra.flywithus.payment.*;
import pl.kubakra.flywithus.tech.id.IdGenerator;
import pl.kubakra.flywithus.tech.time.TimeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class ReservationServiceTestConfiguration {

    public static final LocalDateTime NOW = LocalDateTime.of(2018, 05, 14, 17, 17);
    public static final UUID UUID = java.util.UUID.fromString("486f1894-0297-4441-9341-1e1b7edb9849");

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(timeService(), idGenerator(), reservationRepo(), paymentService());
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
        return new ReservationRepo(paymentService(), paymentRepo());
    }

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(externalPaymentService(), paymentRepo());
    }

    @Bean
    public PaymentRepo paymentRepo() {
        return new PaymentRepo();
    }

    private ExternalPaymentService externalPaymentService() {


        return new ExternalPaymentService() {
            @Override
            public ExternalServicePaymentId registerNewPayment(BigDecimal value) {
                return ExternalPaymentSystemIdTestFactory.fake();
            }

            @Override
            public boolean cancelPayment(ExternalServicePaymentId systemId) {
                return true;
            }
        };
    }

}