package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.util.List;

@RestController
public class FlightPlannerController {

    private FlightPlannerService flightPlannerService;

    public FlightPlannerController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody @Valid AddFlightRequest request, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return flightPlannerService.addFlight(request);

    }


    @DeleteMapping("/admin-api/flights/{id}")
    public void deleteFlight(@PathVariable int id) {
        flightPlannerService.deleteFlight(id);
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight fetchFlight(@PathVariable int id) {
        return flightPlannerService.fetchFlight(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/testing-api/clear")
    public void clearFlights() {
        flightPlannerService.clearFlights();
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(String search) {
        return flightPlannerService.searchAirports(search);
    }

    @GetMapping("/api/flights/{id}")
    public Flight findFlightById(@PathVariable int id) {
        return flightPlannerService.fetchFlight(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@RequestBody @Valid SearchFlightRequest searchFlightRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return flightPlannerService.searchFlights(searchFlightRequest);
    }
}
