package de.hnu.web;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import de.hnu.domain.RideOffer;
import de.hnu.repo.RideOfferRepository;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "http://localhost:4200")
public class RideOfferController {

    private final RideOfferRepository repo;

    public RideOfferController(RideOfferRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public RideOffer create(@RequestBody RideOffer offer) {
        // minimal validation
        if (offer.getDepartureCity() == null || offer.getDestinationCity() == null || offer.getDepartureTime() == null) {
            throw new IllegalArgumentException("departureCity, destinationCity, departureTime are required");
        }
        return repo.save(offer);
    }

    @GetMapping
    public List<RideOffer> search(
            @RequestParam String departureCity,
            @RequestParam String destinationCity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date == null) {
            return repo.findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCase(departureCity, destinationCity);
        }

        Instant from = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant to = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        return repo.findByDepartureCityIgnoreCaseAndDestinationCityIgnoreCaseAndDepartureTimeBetween(
                departureCity, destinationCity, from, to
        );
    }
}