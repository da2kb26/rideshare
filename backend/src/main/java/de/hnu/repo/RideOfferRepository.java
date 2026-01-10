package de.hnu.repo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.hnu.domain.RideOffer;

public interface RideOfferRepository extends MongoRepository<RideOffer, String> {

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