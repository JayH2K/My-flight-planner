package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlightPlannerService {

    long currentId = 10;

    private FlightRepository flightRepository;

    public FlightPlannerService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest request) {
        long newId = currentId;
        currentId++;
        //Creating flight
        Flight flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(), request.getDepartureTime(), request.getArrivalTime(),newId);
        //Error handling
        if (listFlights().stream().anyMatch(flight::equals)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (flight.hasSameAirports()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //Adding and returning flight
        flightRepository.addFlight(flight);
        return flight;
    }

    public synchronized void clearFlights() {
        flightRepository.clearFlights();
    }

    public List<Flight> listFlights()
    {
        return flightRepository.listFlights();
    }

    public synchronized HttpStatus deleteFlight(long flightId) {
        if (flightRepository.listFlights().stream().anyMatch(flight -> flight.getId() == flightId)) {
            flightRepository.deleteFlight(flightId);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public Flight fetchFlight(long flightId) {
        return flightRepository.fetchFlight(flightId);
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }


    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        return flightRepository.searchFlights(searchFlightRequest);
    }

    public Flight findFlightById(Long id) {
        return flightRepository.findFlightById(id);
    }
}
