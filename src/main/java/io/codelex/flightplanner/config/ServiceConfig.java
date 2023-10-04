package io.codelex.flightplanner.config;

import io.codelex.flightplanner.FlightPlannerDatabaseService;
import io.codelex.flightplanner.FlightPlannerInMemoryService;
import io.codelex.flightplanner.FlightPlannerService;
import io.codelex.flightplanner.repository.AirportRepository;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    @ConditionalOnProperty(prefix="flightplanner", name="service.version", havingValue = "in-memory")
    public FlightPlannerService getInMemoryVersion() {
        FlightInMemoryRepository flightInMemoryRepository = new FlightInMemoryRepository();
        return new FlightPlannerInMemoryService(flightInMemoryRepository);
    }


    @Bean
    @ConditionalOnProperty(prefix="flightplanner", name="service.version", havingValue = "database")
    public FlightPlannerService getDatabaseVersion(FlightRepository flightRepository, AirportRepository airportRepository) {
        return new FlightPlannerDatabaseService(flightRepository, airportRepository);
    }
}
