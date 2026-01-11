package de.hnu.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.hnu.domain.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {
    Optional<Ride> findByRideOfferId(String rideOfferId);
    List<Ride> findByDriverPersonId(String driverPersonId);
}
