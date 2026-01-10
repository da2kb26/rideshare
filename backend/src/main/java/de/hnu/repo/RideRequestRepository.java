package de.hnu.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.hnu.domain.RideRequest;

public interface RideRequestRepository extends MongoRepository<RideRequest, String> {
    List<RideRequest> findByRideOfferId(String rideOfferId);
}