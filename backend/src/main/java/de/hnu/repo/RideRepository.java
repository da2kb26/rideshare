package de.hnu.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.Ride;

public interface RideRepository extends JpaRepository<Ride, String> {
    Optional<Ride> findByRideOfferId(String rideOfferId);
    List<Ride> findByDriverPersonId(String driverPersonId);
}
