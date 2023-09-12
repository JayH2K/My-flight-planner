package io.codelex.flightplanner;

import io.codelex.flightplanner.Errors.ValidationException;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

@RestController
public class FlightPlannerController {
    Random r = new Random();

    FlightPlannerService flightPlannerService;

    public FlightPlannerController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody @Valid AddFlightRequest request, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.valueOf(400));
        }
        //Generating new id
        boolean loop = true;
        Long newId = null;
        while (loop) {

            Long id = r.nextLong(100000000);

            if (flightPlannerService.listFlights().stream().anyMatch(flightX -> id == flightX.getId())) {
                break;
            } else {
                loop = false;
                newId = id;
            }
        }
        //Creating flight
        Flight flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(), request.getDepartureTime(), request.getArrivalTime(),newId);
        //Error handling
        if (flightPlannerService.listFlights().stream().anyMatch(flight::equals)) {
            throw new ResponseStatusException(HttpStatus.valueOf(409));
        }
        if (flight.hasSameAirports()) {
            throw new ResponseStatusException(HttpStatus.valueOf(400));
        }
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.valueOf(400));
        }
        //Adding and returning flight
        flightPlannerService.addFlight(flight);
        return flight;
    }


    @DeleteMapping("/admin-api/flights/{id}")
    public HttpStatus deleteFlight(@PathVariable long id) {
        if (flightPlannerService.listFlights().stream().anyMatch(flight -> flight.getId() == id)) {
            flightPlannerService.deleteFlight(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight fetchFlight(@PathVariable long id) {
       return flightPlannerService.fetchFlight(id);
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
    public Flight findFlightById(@PathVariable String id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(400));
        }
        return flightPlannerService.findFlightById(id);
    }

    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@RequestBody @Valid SearchFlightRequest searchFlightRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.valueOf(400));
        }
        return flightPlannerService.searchFlights(searchFlightRequest);
    }
}
