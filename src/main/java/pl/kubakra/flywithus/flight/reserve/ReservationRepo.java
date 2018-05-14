package pl.kubakra.flywithus.flight.reserve;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ReservationRepo {

    private static final Map<UUID, Reservation> RESERVATIONS = new HashMap<>();

    public Reservation save(Reservation reservation) {
        RESERVATIONS.put(reservation.id(), reservation);
        return reservation;
    }

    public Reservation getBy(String id) {
        return RESERVATIONS.get(id);
    }

}