package de.hnu.repo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.RideOffer;

public interface RideOfferRepository extends JpaRepository<RideOffer, String> {

    List<RideOffer> findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCase(
            String departureCity,
            String destinationCity
    );

    List<RideOffer> findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCaseAndDepartureTimeBetween(
            String departureCity,
            String destinationCity,
            Instant from,
            Instant to
    );
}