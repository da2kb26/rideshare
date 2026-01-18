package de.hnu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, String> {
    List<Passenger> findByRideId(String rideId);
    List<Passenger> findByPersonId(String personId);
}