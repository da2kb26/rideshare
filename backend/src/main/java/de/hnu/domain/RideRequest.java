package de.hnu.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import de.hnu.domain.enums.PaymentMethod;
import de.hnu.domain.enums.RideRequestStatus;

@Document("riderequests")
public class RideRequest {

    @Id
    private String id;

    private String rideOfferId;

    // passenger identity
    private String personId;

    // trip details (passenger-specific)
    private String pickupLocation;
    private String dropoffLocation;

    private Instant timestamp;

    private Integer luggageCount;
    private Boolean pet;
    private Boolean kid;

    private PaymentMethod paymentMethod;

    // lifecycle
    private RideRequestStatus status;

    // filled once accepted
    private String rideId;
    private String passengerId;

    public RideRequest() {}

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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Integer luggageCount) {
        this.luggageCount = luggageCount;
    }

    public Boolean getPet() {
        return pet;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    public Boolean getKid() {
        return kid;
    }

    public void setKid(Boolean kid) {
        this.kid = kid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }
}