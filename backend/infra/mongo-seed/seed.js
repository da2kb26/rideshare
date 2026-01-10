db = db.getSiblingDB("sharearide");

// Clear collections (idempotent)
db.insurance.deleteMany({});
db.car.deleteMany({});
db.person.deleteMany({});
db.rideoffers.deleteMany({});

// Insurance
db.insurance.insertMany([
  {
    _id: "ins_1",
    name: "Allianz",
    policyName: "Standard Coverage",
    policyNumber: "AZ-123-456",
    coverage: "Haftpflicht",
    passengerAccidentInsurance: true,
    passengerDriverInsurance: true
  }
]);

// Car
db.car.insertMany([
  {
    _id: "car_1",
    make: "Volkswagen",
    model: "Golf",
    availableSeats: 4,
    luggageSpacePerPerson: 1,
    smokingAllowed: false,
    petsAllowed: true,
    plate: "UL-AB-1234",
    buildYear: 2019,
    insuranceId: "ins_1"
  }
]);

// Person (driver)
db.person.insertMany([
  {
    _id: "person_driver_1",
    name: "Max Mustermann",
    gender: "male",
    age: 29,
    homeCity: "Neu-Ulm",
    bio: "On-time driver, calm music, short breaks possible.",
    phoneNumber: "+49 170 0000000",
    email: "max@example.com",
    street: "Example Street 1",
    postalCode: "89231",
    preferredTransmission: ["AUTOMATIC"],
    preferredCarType: ["HATCHBACK"],
    preferredMusic: ["POP", "ELECTRONIC"],
    needChildSeat: false,
    carId: "car_1",
    languages: ["DE", "EN"],
    chatinessLevel: 2,
    overallKmCovered: 42000
  }
]);

// RideOffers
db.rideoffers.insertMany([
  {
    _id: "offer_1",
    departureCity: "Neu-Ulm",
    destinationCity: "Munich",
    departureTime: new Date("2026-01-15T08:30:00Z"),
    seatsAvailable: 3,
    pricePerPerson: 18.5,
    luggageCount: 3,
    driverPersonId: "person_driver_1"
  },
  {
    _id: "offer_2",
    departureCity: "Ulm",
    destinationCity: "Stuttgart",
    departureTime: new Date("2026-01-16T13:00:00Z"),
    seatsAvailable: 2,
    pricePerPerson: 22.0,
    luggageCount: 2,
    driverPersonId: "person_driver_1"
  }
]);

print("Seed complete.");
printjson({
  insurance: db.insurance.countDocuments(),
  car: db.car.countDocuments(),
  person: db.person.countDocuments(),
  rideoffers: db.rideoffers.countDocuments()
});