package de.hnu.service;

import java.time.Instant;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hnu.data.Passenger;
import de.hnu.data.Ride;
import de.hnu.data.RideOffer;
import de.hnu.data.RideRequest;
import de.hnu.data.enums.RideRequestStatus;
import de.hnu.repo.PassengerRepository;
import de.hnu.repo.RideOfferRepository;
import de.hnu.repo.RideRepository;
import de.hnu.repo.RideRequestRepository;
import de.hnu.web.dto.CreateRideRequestDto;

@Service
public class RideFlowService {

    private final RideRequestRepository rideRequestRepo;
    private final RideRepository rideRepo;
    private final PassengerRepository passengerRepo;
    private final RideOfferRepository rideOfferRepo;
    private final TaskScheduler scheduler;

    public RideFlowService(
            RideRequestRepository rideRequestRepo,
            RideRepository rideRepo,
            PassengerRepository passengerRepo,
            RideOfferRepository rideOfferRepo,
            TaskScheduler scheduler
    ) {
        this.rideRequestRepo = rideRequestRepo;
        this.rideRepo = rideRepo;
        this.passengerRepo = passengerRepo;
        this.rideOfferRepo = rideOfferRepo;
        this.scheduler = scheduler;
    }

    public RideRequest createRideRequest(CreateRideRequestDto dto) {
        RideRequest rr = new RideRequest();
        rr.setRideOfferId(dto.rideOfferId);
        rr.setPersonId(dto.personId);
        rr.setPickupLocation(dto.pickupLocation);
        rr.setDropoffLocation(dto.dropoffLocation);
        rr.setLuggageCount(dto.luggageCount != null ? dto.luggageCount : 0);
        rr.setPet(dto.pet != null ? dto.pet : false);
        rr.setKid(dto.kid != null ? dto.kid : false);
        rr.setPaymentMethod(dto.paymentMethod);
        rr.setTimestamp(Instant.now());
        rr.setStatus(RideRequestStatus.PENDING);

        rr = rideRequestRepo.save(rr);

        // Mock driver acceptance after short delay of a few seconds
        final String requestId = rr.getId();
        scheduler.schedule(
                () -> acceptRideRequest(requestId),
                Instant.now().plusSeconds(7)
        );

        return rr;
    }

    /**
     * Accepts a ride request:
     * - checks capacity on the linked RideOffer
     * - updates seats/luggage on the offer
     * - ensures a Ride exists
     * - creates a Passenger entry
     * - updates the RideRequest status
     *
     * Marked @Transactional so all DB changes are committed together.
     */
    @Transactional
    public RideRequest acceptRideRequest(String rideRequestId) {
        RideRequest rr = rideRequestRepo.findById(rideRequestId)
                .orElseThrow(() -> new IllegalArgumentException("RideRequest not found: " + rideRequestId));

        if (rr.getStatus() != RideRequestStatus.PENDING) {
            // already processed (ACCEPTED or REJECTED) – nothing to do
            return rr;
        }

        int seatsConsumed = 1
                + (Boolean.TRUE.equals(rr.getPet()) ? 1 : 0)
                + (Boolean.TRUE.equals(rr.getKid()) ? 1 : 0);

        int luggageRequested = rr.getLuggageCount() != null ? rr.getLuggageCount() : 0;

        // Load the RideOffer and check capacity
        RideOffer offer = rideOfferRepo.findById(rr.getRideOfferId())
                .orElseThrow(() -> new IllegalArgumentException("RideOffer not found: " + rr.getRideOfferId()));

        Integer seatsAvailable = offer.getSeatsAvailable() != null ? offer.getSeatsAvailable() : 0;
        Integer luggageAvailable = offer.getLuggageCount() != null ? offer.getLuggageCount() : 0;

        if (seatsAvailable < seatsConsumed || luggageAvailable < luggageRequested) {
            rr.setStatus(RideRequestStatus.REJECTED);
            return rideRequestRepo.save(rr);
        }

        // Update offer capacity
        offer.setSeatsAvailable(seatsAvailable - seatsConsumed);
        offer.setLuggageCount(luggageAvailable - luggageRequested);

        var roffer = rideOfferRepo.save(offer);

        // Ensure Ride exists for this offer
        Ride ride = rideRepo.findByRideOfferId(roffer.getId()).orElseGet(() -> {
            Ride r = new Ride();
            r.setRideOfferId(roffer.getId());
            r.setDepartureCity(roffer.getDepartureCity());
            r.setDestinationCity(roffer.getDestinationCity());
            r.setDepartureTime(roffer.getDepartureTime());
            r.setDriverPersonId(roffer.getDriverPersonId());
            return rideRepo.save(r);
        });

        // Cache remaining capacity on Ride (optional but kept for consistency)
        ride.setSeatsRemaining(roffer.getSeatsAvailable());
        ride.setLuggageRemaining(roffer.getLuggageCount());
        ride = rideRepo.save(ride);

        // Create Passenger
        Passenger p = new Passenger();
        p.setRideId(ride.getId());
        p.setPersonId(rr.getPersonId());
        p.setLuggageCount(luggageRequested);
        p.setPaymentMethod(rr.getPaymentMethod());
        p.setPaymentOutstanding(true);
        p.setPickupLocation(rr.getPickupLocation());
        p.setDropoffLocation(rr.getDropoffLocation());
        p.setPet(rr.getPet());
        p.setKid(rr.getKid());
        p.setSeatsConsumed(seatsConsumed);

        p = passengerRepo.save(p);

        // Link back to RideRequest
        rr.setRideId(ride.getId());
        rr.setPassengerId(p.getId());
        rr.setStatus(RideRequestStatus.ACCEPTED);

        return rideRequestRepo.save(rr);
    }
}