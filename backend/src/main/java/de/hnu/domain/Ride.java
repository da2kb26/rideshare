package de.hnu.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("rides")
public class Ride {

    @Id
    private String id;

    private String rideOfferId;

    private String departureCity;
    private String destinationCity;
    private Instant departureTime;

    private String driverPersonId;

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