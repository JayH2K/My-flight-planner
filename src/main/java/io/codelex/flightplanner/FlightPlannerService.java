package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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

    public synchronized HttpStatus deleteFlight(long flightId) {
        if (flightRepository.listFlights().stream().anyMatch(flight -> flight.getId() == flightId)) {
            flightRepository.deleteFlight(flightId);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
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

    public Flight findFlightById(Long id) {
        return flightRepository.findFlightById(id);
    }
}
