package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.AddFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FlightRepository {

    ArrayList<Flight> flightList = new ArrayList<>();

    public void addFlight(Flight flight) {
        this.flightList.add(flight);
    }

    public ArrayList<Flight> listFlights() {
        return this.flightList;
    }

    public void clearFlights() {
        flightList.clear();
    }

    public void deleteFlight(long flightId) {
        flightList.removeIf(flight -> flight.getId() == flightId);
    }

    public Flight fetchFlight(long flightId) {
        Optional<Flight> foundFlight = flightList.stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst();

        if (foundFlight.isPresent()) {
            return foundFlight.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
