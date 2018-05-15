package pl.kubakra.flywithus.flight.reserve;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservationRepo {

    private static final Map<UUID, Reservation> RESERVATIONS = new HashMap<>();

    public Reservation save(Reservation reservation) {
        RESERVATIONS.put(reservation.id(), reservation);
        return reservation;
    }

    public Optional<Reservation> getBy(UUID id) {
        return Optional.ofNullable(RESERVATIONS.get(id));
    }

    public void delete(Reservation reservation) {
        RESERVATIONS.remove(reservation.id());
    }

}