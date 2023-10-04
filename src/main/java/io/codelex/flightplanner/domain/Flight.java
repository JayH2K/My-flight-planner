package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Airport from;

    @NotNull
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Airport to;

    @Column(name = "carrier", columnDefinition = "VARCHAR(255)")
    @NotBlank
    private String carrier;

    @Column(name = "departure_time", columnDefinition = "VARCHAR(255)")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", columnDefinition = "VARCHAR(255)")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;


    public Flight(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean hasSameAirports() {
        return this.from.getAirport().trim().equalsIgnoreCase(this.to.getAirport().trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from) &&
                Objects.equals(to, flight.to) &&
                Objects.equals(carrier, flight.carrier) &&
                Objects.equals(departureTime, flight.departureTime) &&
                Objects.equals(arrivalTime, flight.arrivalTime);
    }
}
