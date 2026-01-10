package de.hnu.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("rideoffers")
public class RideOffer {
    @Id
    private String id;

    private String departureCity;
    private String destinationCity;
    private Instant departureTime;

    private Integer seatsAvailable;
    private Double pricePerPerson;

    private Integer luggageCount;     // NEW: total luggage capacity remaining
    private String driverPersonId;    // NEW: references Person.id

    public RideOffer() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(Double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public Integer getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Integer luggageCount) {
        this.luggageCount = luggageCount;
    }

    public String getDriverPersonId() {
        return driverPersonId;
    }

    public void setDriverPersonId(String driverPersonId) {
        this.driverPersonId = driverPersonId;
    }
}