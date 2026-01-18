package de.hnu.data;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String rideOfferId; // references RideOffer.id

    private String departureCity;
    private String destinationCity;
    private Instant departureTime;

    private String driverPersonId; // references Person.id

    // optional cached remaining capacity (source of truth stays RideOffer)
    private Integer seatsRemaining;
    private Integer luggageRemaining;

    public Ride() {}

    // getters/setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRideOfferId() {
        return rideOfferId;
    }

    public void setRideOfferId(String rideOfferId) {
        this.rideOfferId = rideOfferId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public String getDriverPersonId() {
        return driverPersonId;
    }

    public void setDriverPersonId(String driverPersonId) {
        this.driverPersonId = driverPersonId;
    }

    public Integer getSeatsRemaining() {
        return seatsRemaining;
    }

    public void setSeatsRemaining(Integer seatsRemaining) {
        this.seatsRemaining = seatsRemaining;
    }

    public Integer getLuggageRemaining() {
        return luggageRemaining;
    }

    public void setLuggageRemaining(Integer luggageRemaining) {
        this.luggageRemaining = luggageRemaining;
    }
}