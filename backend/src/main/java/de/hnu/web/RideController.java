package de.hnu.web;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import de.hnu.domain.Passenger;
import de.hnu.domain.Ride;
import de.hnu.repo.PassengerRepository;
import de.hnu.repo.RideRepository;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {

    private final RideRepository rideRepo;
    private final PassengerRepository passengerRepo;

    public RideController(RideRepository rideRepo, PassengerRepository passengerRepo) {
        this.rideRepo = rideRepo;
        this.passengerRepo = passengerRepo;
    }

    @GetMapping("/{id}")
    public Ride getRide(@PathVariable String id) {
        return rideRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found: " + id));
    }

    @GetMapping("/{id}/passengers")
    public List<Passenger> getPassengers(@PathVariable String id) {
        return passengerRepo.findByRideId(id);
    }
}