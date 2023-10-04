package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class FlightPlannerInMemoryService implements FlightPlannerService {

    Integer currentId = 10;

    private FlightInMemoryRepository flightInMemoryRepository;

    public FlightPlannerInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest request) {
        Integer newId = currentId;
        currentId++;
        //Creating flight
        Flight flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(), request.getDepartureTime(), request.getArrivalTime());
        flight.setId(newId);
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
        flightInMemoryRepository.addFlight(flight);
        return flight;
    }

    public synchronized void clearFlights() {
        flightInMemoryRepository.clearFlights();
    }

    public List<Flight> listFlights()
    {
        return flightInMemoryRepository.listFlights();
    }

    public synchronized void deleteFlight(int flightId) {

        flightInMemoryRepository.deleteFlight(flightId);

    }

    public Optional<Flight> fetchFlight(int flightId) {
        return Optional.ofNullable(flightInMemoryRepository.fetchFlight(flightId));
    }

    public List<Airport> searchAirports(String search) {
        return flightInMemoryRepository.searchAirports(search);
    }


    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        return flightInMemoryRepository.searchFlights(searchFlightRequest);
    }

    public Flight findFlightById(int id) {
        return flightInMemoryRepository.findFlightById(id);
    }
}
