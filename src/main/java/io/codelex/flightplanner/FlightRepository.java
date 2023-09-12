package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Airport> searchAirports(String search) {
        List<Airport> found = new ArrayList<>();
        for (Flight x : flightList
        ) {
            if (x.getFrom().toString().toLowerCase().contains(search.toLowerCase().trim())) {
                found.add(x.getFrom());
            }
            if (x.getTo().toString().toLowerCase().contains(search.toLowerCase().trim())) {
                found.add(x.getTo());
            }
        }
        return found;
    }

    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().matches(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
    }
        List<Flight> matchedFlights = new ArrayList<>();
        for (Flight x : flightList
             ) {
            if (x.getFrom().getAirport().matches(searchFlightRequest.getFrom())) {
                if (x.getTo().getAirport().matches(searchFlightRequest.getTo())) {
                    if (x.getDepartureTime().isAfter(searchFlightRequest.getDepartureDate().atStartOfDay())) {
                        matchedFlights.add(x);
                    }
                }
            }
        }
        return new PageResult<>(0,0,matchedFlights);
    }

    public Flight findFlightById(String id) {
        Optional<Flight> foundFlight = flightList.stream()
                .filter(flight -> flight.getId() == Long.parseLong(id))
                .findFirst();
        if (foundFlight.isPresent()) {
            return foundFlight.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
