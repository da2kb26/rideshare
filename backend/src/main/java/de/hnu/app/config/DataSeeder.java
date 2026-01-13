package de.hnu.app.config;

import java.time.Instant;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.hnu.data.Car;
import de.hnu.data.Insurance;
import de.hnu.data.Person;
import de.hnu.data.RideOffer;
import de.hnu.data.enums.Coverage;
import de.hnu.repo.CarRepository;
import de.hnu.repo.InsuranceRepository;
import de.hnu.repo.PassengerRepository;
import de.hnu.repo.PersonRepository;
import de.hnu.repo.RideOfferRepository;
import de.hnu.repo.RideRepository;
import de.hnu.repo.RideRequestRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final InsuranceRepository insuranceRepo;
    private final CarRepository carRepo;
    private final PersonRepository personRepo;
    private final RideOfferRepository rideOfferRepo;
    private final RideRequestRepository rideRequestRepo;
    private final RideRepository rideRepo;
    private final PassengerRepository passengerRepo;

    public DataSeeder(
            InsuranceRepository insuranceRepo,
            CarRepository carRepo,
            PersonRepository personRepo,
            RideOfferRepository rideOfferRepo,
            RideRequestRepository rideRequestRepo,
            RideRepository rideRepo,
            PassengerRepository passengerRepo
    ) {
        this.insuranceRepo = insuranceRepo;
        this.carRepo = carRepo;
        this.personRepo = personRepo;
        this.rideOfferRepo = rideOfferRepo;
        this.rideRequestRepo = rideRequestRepo;
        this.rideRepo = rideRepo;
        this.passengerRepo = passengerRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // behave like /docker-entrypoint-initdb.d: seed only on first DB init
        if (insuranceRepo.count() > 0
                || carRepo.count() > 0
                || personRepo.count() > 0
                || rideOfferRepo.count() > 0) {
            return;
        }

        // Clear other tables just in case (should be empty on first run anyway)
        rideRequestRepo.deleteAll();
        rideRepo.deleteAll();
        passengerRepo.deleteAll();

        // =========================
        // INSURANCE (3 entries)
        // =========================

        Insurance ins1 = new Insurance();
        ins1.setId("ins_1");
        ins1.setName("Allianz");
        ins1.setPolicyName("Basis Schutz");
        ins1.setPolicyNumber("AZ-123-456");
        ins1.setCoverage(Coverage.Haftpflicht);   // enum: Haftpflicht | Teilkasko | Vollkasko
        ins1.setPassengerDriverInsurance(true);
        insuranceRepo.save(ins1);

        Insurance ins2 = new Insurance();
        ins2.setId("ins_2");
        ins2.setName("HUK-Coburg");
        ins2.setPolicyName("Komfort");
        ins2.setPolicyNumber("HUK-234-567");
        ins2.setCoverage(Coverage.Teilkasko);
        ins2.setPassengerDriverInsurance(true);
        insuranceRepo.save(ins2);

        Insurance ins3 = new Insurance();
        ins3.setId("ins_3");
        ins3.setName("DEVK");
        ins3.setPolicyName("Premium");
        ins3.setPolicyNumber("DEVK-345-678");
        ins3.setCoverage(Coverage.Vollkasko);
        ins3.setPassengerDriverInsurance(true);
        insuranceRepo.save(ins3);

        // =========================
        // CARS (3 entries)
        // =========================

        Car car1 = new Car();
        car1.setId("car_1");
        car1.setMake("Volkswagen");
        car1.setModel("Golf");
        car1.setAvailableSeats(4);
        car1.setLuggageSpace(3);
        car1.setSmokingAllowed(false);
        car1.setPetsAllowed(true);
        car1.setPlate("UL-AB-1234");
        car1.setBuildYear(2019);
        car1.setInsuranceId("ins_1");
        carRepo.save(car1);

        Car car2 = new Car();
        car2.setId("car_2");
        car2.setMake("BMW");
        car2.setModel("320i");
        car2.setAvailableSeats(3);
        car2.setLuggageSpace(2);
        car2.setSmokingAllowed(false);
        car2.setPetsAllowed(false);
        car2.setPlate("UL-CD-5678");
        car2.setBuildYear(2021);
        car2.setInsuranceId("ins_2");
        carRepo.save(car2);

        Car car3 = new Car();
        car3.setId("car_3");
        car3.setMake("Mercedes-Benz");
        car3.setModel("C200");
        car3.setAvailableSeats(4);
        car3.setLuggageSpace(4);
        car3.setSmokingAllowed(true);
        car3.setPetsAllowed(true);
        car3.setPlate("NU-EF-9012");
        car3.setBuildYear(2018);
        car3.setInsuranceId("ins_3");
        carRepo.save(car3);

        // =========================
        // PERSONS (3 drivers + 10 passengers)
        // =========================

        // Drivers
        Person driver1 = new Person();
        driver1.setId("person_driver_1");
        driver1.setName("Max Mustermann");
        driver1.setGender("male");
        driver1.setAge(29);
        driver1.setHomeCity("Neu-Ulm");
        driver1.setBio("On-time driver, calm music, short breaks possible.");
        driver1.setPhoneNumber("+49 170 0000000");
        driver1.setEmail("max@example.com");
        driver1.setStreet("Example Street 1");
        driver1.setPostalCode("89231");
        driver1.setPreferredTransmission(List.of("AUTOMATIC"));
        driver1.setPreferredCarType(List.of("HATCHBACK"));
        driver1.setPreferredMusic(List.of("POP", "ELECTRONIC"));
        driver1.setCarId("car_1");
        driver1.setLanguages(List.of("DE", "EN"));
        driver1.setChatinessLevel(2);
        driver1.setOverallKmCovered(42000.0);
        personRepo.save(driver1);

        Person driver2 = new Person();
        driver2.setId("person_driver_2");
        driver2.setName("Sophie Becker");
        driver2.setGender("female");
        driver2.setAge(31);
        driver2.setHomeCity("Ulm");
        driver2.setBio("No smoking, no pets. I like punctuality.");
        driver2.setPhoneNumber("+49 170 1111111");
        driver2.setEmail("sophie@example.com");
        driver2.setStreet("Donauweg 12");
        driver2.setPostalCode("89073");
        driver2.setPreferredTransmission(List.of("MANUAL", "AUTOMATIC"));
        driver2.setPreferredCarType(List.of("SEDAN"));
        driver2.setPreferredMusic(List.of("ROCK", "INDIE"));
        driver2.setCarId("car_2");
        driver2.setLanguages(List.of("DE", "EN"));
        driver2.setChatinessLevel(1);
        driver2.setOverallKmCovered(68000.0);
        personRepo.save(driver2);

        Person driver3 = new Person();
        driver3.setId("person_driver_3");
        driver3.setName("Ali Hassan");
        driver3.setGender("male");
        driver3.setAge(34);
        driver3.setHomeCity("Neu-Ulm");
        driver3.setBio("Friendly driver, flexible pickup if on the route.");
        driver3.setPhoneNumber("+49 170 2222222");
        driver3.setEmail("ali@example.com");
        driver3.setStreet("Ringstraße 5");
        driver3.setPostalCode("89233");
        driver3.setPreferredTransmission(List.of("AUTOMATIC"));
        driver3.setPreferredCarType(List.of("SEDAN", "WAGON"));
        driver3.setPreferredMusic(List.of("HIPHOP", "POP"));
        driver3.setCarId("car_3");
        driver3.setLanguages(List.of("DE", "EN", "AR"));
        driver3.setChatinessLevel(3);
        driver3.setOverallKmCovered(95000.0);
        personRepo.save(driver3);

        // Passengers 1..10
        Person p1 = new Person();
        p1.setId("person_passenger_1");
        p1.setName("Anna Müller");
        p1.setGender("female");
        p1.setAge(25);
        p1.setHomeCity("Ulm");
        p1.setBio("Student.");
        p1.setPhoneNumber("+49 170 3000001");
        p1.setEmail("anna@example.com");
        p1.setStreet("Bergstraße 3");
        p1.setPostalCode("89075");
        p1.setPreferredTransmission(List.of());
        p1.setPreferredCarType(List.of());
        p1.setPreferredMusic(List.of());
        p1.setCarId(null);
        p1.setLanguages(List.of("DE"));
        p1.setChatinessLevel(2);
        p1.setOverallKmCovered(8000.0);
        personRepo.save(p1);

        Person p2 = new Person();
        p2.setId("person_passenger_2");
        p2.setName("Jonas Klein");
        p2.setGender("male");
        p2.setAge(27);
        p2.setHomeCity("Neu-Ulm");
        p2.setBio("One suitcase.");
        p2.setPhoneNumber("+49 170 3000002");
        p2.setEmail("jonas@example.com");
        p2.setStreet("Wiesenweg 9");
        p2.setPostalCode("89231");
        p2.setPreferredTransmission(List.of());
        p2.setPreferredCarType(List.of());
        p2.setPreferredMusic(List.of());
        p2.setCarId(null);
        p2.setLanguages(List.of("DE", "EN"));
        p2.setChatinessLevel(1);
        p2.setOverallKmCovered(12000.0);
        personRepo.save(p2);

        Person p3 = new Person();
        p3.setId("person_passenger_3");
        p3.setName("Laura Schmidt");
        p3.setGender("female");
        p3.setAge(30);
        p3.setHomeCity("Ulm");
        p3.setBio("Quiet passenger.");
        p3.setPhoneNumber("+49 170 3000003");
        p3.setEmail("laura@example.com");
        p3.setStreet("Gartenweg 2");
        p3.setPostalCode("89073");
        p3.setPreferredTransmission(List.of());
        p3.setPreferredCarType(List.of());
        p3.setPreferredMusic(List.of());
        p3.setCarId(null);
        p3.setLanguages(List.of("DE", "EN"));
        p3.setChatinessLevel(0);
        p3.setOverallKmCovered(25000.0);
        personRepo.save(p3);

        Person p4 = new Person();
        p4.setId("person_passenger_4");
        p4.setName("Peter Wagner");
        p4.setGender("male");
        p4.setAge(33);
        p4.setHomeCity("Stuttgart");
        p4.setBio("Business travel.");
        p4.setPhoneNumber("+49 170 3000004");
        p4.setEmail("peter@example.com");
        p4.setStreet("Parkallee 7");
        p4.setPostalCode("70173");
        p4.setPreferredTransmission(List.of());
        p4.setPreferredCarType(List.of());
        p4.setPreferredMusic(List.of());
        p4.setCarId(null);
        p4.setLanguages(List.of("DE", "EN"));
        p4.setChatinessLevel(2);
        p4.setOverallKmCovered(40000.0);
        personRepo.save(p4);

        Person p5 = new Person();
        p5.setId("person_passenger_5");
        p5.setName("Mira Fischer");
        p5.setGender("female");
        p5.setAge(24);
        p5.setHomeCity("Munich");
        p5.setBio("Sometimes with pet.");
        p5.setPhoneNumber("+49 170 3000005");
        p5.setEmail("mira@example.com");
        p5.setStreet("Sonnenstraße 1");
        p5.setPostalCode("80331");
        p5.setPreferredTransmission(List.of());
        p5.setPreferredCarType(List.of());
        p5.setPreferredMusic(List.of());
        p5.setCarId(null);
        p5.setLanguages(List.of("DE", "EN"));
        p5.setChatinessLevel(3);
        p5.setOverallKmCovered(9000.0);
        personRepo.save(p5);

        Person p6 = new Person();
        p6.setId("person_passenger_6");
        p6.setName("Tim Bauer");
        p6.setGender("male");
        p6.setAge(28);
        p6.setHomeCity("Ulm");
        p6.setBio("One bag.");
        p6.setPhoneNumber("+49 170 3000006");
        p6.setEmail("tim@example.com");
        p6.setStreet("Donaustraße 10");
        p6.setPostalCode("89073");
        p6.setPreferredTransmission(List.of());
        p6.setPreferredCarType(List.of());
        p6.setPreferredMusic(List.of());
        p6.setCarId(null);
        p6.setLanguages(List.of("DE"));
        p6.setChatinessLevel(2);
        p6.setOverallKmCovered(15000.0);
        personRepo.save(p6);

        Person p7 = new Person();
        p7.setId("person_passenger_7");
        p7.setName("Sara Nguyen");
        p7.setGender("female");
        p7.setAge(26);
        p7.setHomeCity("Neu-Ulm");
        p7.setBio("Prefers calm rides.");
        p7.setPhoneNumber("+49 170 3000007");
        p7.setEmail("sara@example.com");
        p7.setStreet("Marktplatz 4");
        p7.setPostalCode("89231");
        p7.setPreferredTransmission(List.of());
        p7.setPreferredCarType(List.of());
        p7.setPreferredMusic(List.of());
        p7.setCarId(null);
        p7.setLanguages(List.of("DE", "EN"));
        p7.setChatinessLevel(1);
        p7.setOverallKmCovered(7000.0);
        personRepo.save(p7);

        Person p8 = new Person();
        p8.setId("person_passenger_8");
        p8.setName("David Meier");
        p8.setGender("male");
        p8.setAge(35);
        p8.setHomeCity("Berlin");
        p8.setBio("Two bags often.");
        p8.setPhoneNumber("+49 170 3000008");
        p8.setEmail("david@example.com");
        p8.setStreet("Hauptstraße 20");
        p8.setPostalCode("10115");
        p8.setPreferredTransmission(List.of());
        p8.setPreferredCarType(List.of());
        p8.setPreferredMusic(List.of());
        p8.setCarId(null);
        p8.setLanguages(List.of("DE", "EN"));
        p8.setChatinessLevel(2);
        p8.setOverallKmCovered(52000.0);
        personRepo.save(p8);

        Person p9 = new Person();
        p9.setId("person_passenger_9");
        p9.setName("Elena Rossi");
        p9.setGender("female");
        p9.setAge(29);
        p9.setHomeCity("Ulm");
        p9.setBio("Weekend traveler.");
        p9.setPhoneNumber("+49 170 3000009");
        p9.setEmail("elena@example.com");
        p9.setStreet("Kastanienweg 6");
        p9.setPostalCode("89075");
        p9.setPreferredTransmission(List.of());
        p9.setPreferredCarType(List.of());
        p9.setPreferredMusic(List.of());
        p9.setCarId(null);
        p9.setLanguages(List.of("DE", "EN", "IT"));
        p9.setChatinessLevel(3);
        p9.setOverallKmCovered(31000.0);
        personRepo.save(p9);

        Person p10 = new Person();
        p10.setId("person_passenger_10");
        p10.setName("Felix Hartmann");
        p10.setGender("male");
        p10.setAge(23);
        p10.setHomeCity("Neu-Ulm");
        p10.setBio("No luggage mostly.");
        p10.setPhoneNumber("+49 170 3000010");
        p10.setEmail("felix@example.com");
        p10.setStreet("Schillerstraße 8");
        p10.setPostalCode("89233");
        p10.setPreferredTransmission(List.of());
        p10.setPreferredCarType(List.of());
        p10.setPreferredMusic(List.of());
        p10.setCarId(null);
        p10.setLanguages(List.of("DE"));
        p10.setChatinessLevel(2);
        p10.setOverallKmCovered(4000.0);
        personRepo.save(p10);

        // =========================
        // RIDEOFFERS (5 entries)
        // =========================

        RideOffer offer1 = new RideOffer();
        offer1.setId("offer_1");
        offer1.setDepartureCity("Neu-Ulm");
        offer1.setDestinationCity("Munich");
        offer1.setDepartureTime(Instant.parse("2026-01-15T08:30:00Z"));
        offer1.setSeatsAvailable(3);
        offer1.setPricePerPerson(18.5);
        offer1.setLuggageCount(3);
        offer1.setDriverPersonId("person_driver_1");
        rideOfferRepo.save(offer1);

        RideOffer offer2 = new RideOffer();
        offer2.setId("offer_2");
        offer2.setDepartureCity("Ulm");
        offer2.setDestinationCity("Stuttgart");
        offer2.setDepartureTime(Instant.parse("2026-01-16T13:00:00Z"));
        offer2.setSeatsAvailable(2);
        offer2.setPricePerPerson(22.0);
        offer2.setLuggageCount(2);
        offer2.setDriverPersonId("person_driver_1");
        rideOfferRepo.save(offer2);

        RideOffer offer3 = new RideOffer();
        offer3.setId("offer_3");
        offer3.setDepartureCity("Ulm");
        offer3.setDestinationCity("Munich");
        offer3.setDepartureTime(Instant.parse("2026-01-17T07:15:00Z"));
        offer3.setSeatsAvailable(2);
        offer3.setPricePerPerson(20.0);
        offer3.setLuggageCount(2);
        offer3.setDriverPersonId("person_driver_2");
        rideOfferRepo.save(offer3);

        RideOffer offer4 = new RideOffer();
        offer4.setId("offer_4");
        offer4.setDepartureCity("Neu-Ulm");
        offer4.setDestinationCity("Berlin");
        offer4.setDepartureTime(Instant.parse("2026-01-18T06:45:00Z"));
        offer4.setSeatsAvailable(3);
        offer4.setPricePerPerson(35.0);
        offer4.setLuggageCount(4);
        offer4.setDriverPersonId("person_driver_3");
        rideOfferRepo.save(offer4);

        RideOffer offer5 = new RideOffer();
        offer5.setId("offer_5");
        offer5.setDepartureCity("Stuttgart");
        offer5.setDestinationCity("Ulm");
        offer5.setDepartureTime(Instant.parse("2026-01-19T17:30:00Z"));
        offer5.setSeatsAvailable(2);
        offer5.setPricePerPerson(15.0);
        offer5.setLuggageCount(2);
        offer5.setDriverPersonId("person_driver_2");
        rideOfferRepo.save(offer5);

        System.out.println("DataSeeder: DerbyDB seeded with initial data.");
    }
}