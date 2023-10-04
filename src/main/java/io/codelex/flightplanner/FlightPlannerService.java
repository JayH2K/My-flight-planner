package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FlightPlannerService {

    Flight addFlight(AddFlightRequest request);

    void deleteFlight(int flightId);

    Optional<Flight> fetchFlight(int flightId);

    void clearFlights();

    List<Airport> searchAirports(String search);

    PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest);
}