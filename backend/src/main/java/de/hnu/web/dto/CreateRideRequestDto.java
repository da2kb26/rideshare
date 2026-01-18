package de.hnu.web.dto;

import de.hnu.data.enums.PaymentMethod;

public class CreateRideRequestDto {
    public String rideOfferId;
    public String personId;
    public String pickupLocation;
    public String dropoffLocation;

    public Integer luggageCount;
    public Boolean pet;
    public Boolean kid;

    public PaymentMethod paymentMethod;
}