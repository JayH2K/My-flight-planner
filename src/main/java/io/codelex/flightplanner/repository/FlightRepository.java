package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT DISTINCT f FROM Flight f WHERE f.from.airport = :from AND f.to.airport = :to AND CAST(f.departureTime AS LocalDate) = :departureTime")
    List<Flight> searchFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("departureTime") LocalDate departureTime
    );
}