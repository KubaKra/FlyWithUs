package pl.kubakra.flywithus.flight;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationTest {

    private static final LocalDateTime DAY = LocalDateTime.of(2018, 05, 15, 11, 13);
    private static final LocalDateTime BEFORE_DAY = DAY.minusDays(1);
    private static final LocalDateTime AFTER_DAY = DAY.plusDays(1);


    @Test
    public void datesAreBefore() {

        // given
        Flight.Duration duration = new Flight.Duration(BEFORE_DAY, BEFORE_DAY);

        // when
        boolean datesAreBefore = duration.areDatesBefore(DAY);

        // then
        assertThat(datesAreBefore).isTrue();
    }

    @Test
    public void departureDateIsAfter() {

        // given
        Flight.Duration duration = new Flight.Duration(AFTER_DAY, BEFORE_DAY);

        // when
        boolean datesAreBefore = duration.areDatesBefore(DAY);

        // then
        assertThat(datesAreBefore).isFalse();
    }

    @Test
    public void arrivalDateIsAfter() {

        // given
        Flight.Duration duration = new Flight.Duration(BEFORE_DAY, AFTER_DAY);

        // when
        boolean datesAreBefore = duration.areDatesBefore(DAY);

        // then
        assertThat(datesAreBefore).isFalse();
    }

}