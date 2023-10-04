package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.repository.AirportRepository;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FlightPlannerDatabaseService implements FlightPlannerService {

    int currentId = 10;

    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    public FlightPlannerDatabaseService(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest request) {
        int newId = currentId;
        currentId++;
        //Creating flight
        Flight flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(), request.getDepartureTime(), request.getArrivalTime());
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
        flightRepository.save(flight);
        return flight;
    }

    @Override
    public synchronized void deleteFlight(int flightId) {
        flightRepository.deleteById(flightId);
    }

    public void clearFlights() {
        flightRepository.deleteAll();
    }

    public List<Flight> listFlights()
    {
        return flightRepository.findAll();
    }

    public Optional<Flight> fetchFlight(int flightId) {
        return flightRepository.findById(flightId);
    }

    public List<Airport> searchAirports(String search) {
        return airportRepository.searchAirports(search.trim().toLowerCase());
    }


    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        List<Flight> results = flightRepository.searchFlights(searchFlightRequest.getFrom(),searchFlightRequest.getTo(),searchFlightRequest.getDepartureDate());
        return new PageResult<>(0,results.size(),results);
    }

    public Optional<Flight> findFlightById(int id) {
        return flightRepository.findById(id);
    }
}
