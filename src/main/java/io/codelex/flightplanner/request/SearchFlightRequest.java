package io.codelex.flightplanner.request;

import io.codelex.flightplanner.domain.Airport;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SearchFlightRequest {

    String from;

    String to;

    LocalDate departureDate;

    public SearchFlightRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}
