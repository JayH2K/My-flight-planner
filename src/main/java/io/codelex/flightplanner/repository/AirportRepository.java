package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.page.PageResult;
import io.codelex.flightplanner.request.SearchFlightRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Query("SELECT DISTINCT a FROM Airport a WHERE LOWER(a.airport) LIKE %:searchText% OR LOWER(a.city) LIKE %:searchText% OR LOWER(a.country) LIKE %:searchText%")
    List<Airport> searchAirports(@Param("searchText") String searchText);
}
