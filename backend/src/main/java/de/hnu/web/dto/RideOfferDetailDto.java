package de.hnu.web.dto;

import de.hnu.data.Car;
import de.hnu.data.Insurance;
import de.hnu.data.Person;
import de.hnu.data.RideOffer;

public class RideOfferDetailDto {
    public RideOffer offer;
    public Person driver;
    public Car car;
    public Insurance insurance;
}