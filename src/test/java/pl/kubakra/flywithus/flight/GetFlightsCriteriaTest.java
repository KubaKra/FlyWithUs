package pl.kubakra.flywithus.flight;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class GetFlightsCriteriaTest {

    private static final LocalDate NOW = LocalDate.of(2018, 05, 15);

    @Test
    public void whenIsAfterNow() {

        // given
        GetFlightsCriteria getFlightsCriteria =
                new GetFlightsCriteria("Warsaw", "Singapoure", NOW.plusDays(1), null, 1);

        // when
        boolean datesAreAfterThanNOW = getFlightsCriteria.areDatesAfterThan(NOW);

        // then
        assertThat(datesAreAfterThanNOW).isTrue();
    }

    @Test
    public void whenAndReturnDateAreAfterNow() {

        // given
        GetFlightsCriteria getFlightsCriteria =
                new GetFlightsCriteria("Warsaw", "Singapoure", NOW.plusDays(1), NOW.plusDays(3), 1);

        // when
        boolean datesAreAfterThanNOW = getFlightsCriteria.areDatesAfterThan(NOW);

        // then
        assertThat(datesAreAfterThanNOW).isTrue();
    }

    @Test
    public void whenIsBeforeNow() {

        // given
        GetFlightsCriteria getFlightsCriteria =
                new GetFlightsCriteria("Warsaw", "Singapoure", NOW.minusDays(1), null, 1);

        // when
        boolean datesAreAfterThanNOW = getFlightsCriteria.areDatesAfterThan(NOW);

        // then
        assertThat(datesAreAfterThanNOW).isFalse();
    }

    @Test
    public void returnDateIsBeforeNow() {

        // given
        GetFlightsCriteria getFlightsCriteria =
                new GetFlightsCriteria("Warsaw", "Singapoure", NOW.plusDays(1), NOW.minusDays(1), 1);

        // when
        boolean datesAreAfterThanNOW = getFlightsCriteria.areDatesAfterThan(NOW);

        // then
        assertThat(datesAreAfterThanNOW).isFalse();
    }

}