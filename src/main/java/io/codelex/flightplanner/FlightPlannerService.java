package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class FlightPlannerService {

    FlightRepository flightRepository;

    public FlightPlannerService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized void addFlight(Flight flight) {
        flightRepository.addFlight(flight);
    }

    public synchronized void clearFlights() {
        flightRepository.clearFlights();
    }

    public synchronized List<Flight> listFlights()
    {
        return flightRepository.listFlights();
    }

    public synchronized void deleteFlight(long flightId) {
        flightRepository.deleteFlight(flightId);
    }

    public synchronized Flight fetchFlight(long flightId) {
        return flightRepository.fetchFlight(flightId);
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }


    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        return flightRepository.searchFlights(searchFlightRequest);
    }

    public Flight findFlightById(String id) {
        return flightRepository.findFlightById(id);
    }
}
