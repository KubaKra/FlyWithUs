package pl.kubakra.flywithus.flight.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;

class ReservationRequest {

    @JsonProperty
    private String user;
    @JsonProperty
    private boolean quickCheckIn;
    @JsonProperty
    private int peopleCount;

    public String getUser() {
        return user;
    }

    public boolean isQuickCheckIn() {
        return quickCheckIn;
    }

    public int getPeopleCount() {
        return 1;
    }

}