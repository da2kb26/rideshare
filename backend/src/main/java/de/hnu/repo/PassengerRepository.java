package de.hnu.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.hnu.domain.Passenger;

public interface PassengerRepository extends MongoRepository<Passenger, String> {
    List<Passenger> findByRideId(String rideId);
}