package pl.kubakra.flywithus.flight.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;

class ReservationRequest {

    @JsonProperty
    private String user;
    @JsonProperty
    private boolean quickCheckIn;

    public String getUser() {
        return user;
    }

}