package de.hnu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.RideRequest;

public interface RideRequestRepository extends JpaRepository<RideRequest, String> {
    List<RideRequest> findByRideOfferId(String rideOfferId);
}