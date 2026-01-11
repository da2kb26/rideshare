// Runs via /docker-entrypoint-initdb.d on FIRST initialization only

db = db.getSiblingDB("sharearide");

// Helper: always get collection explicitly
function col(name) { return db.getCollection(name); }

// Clear  collections
col("insurance").deleteMany({});
col("car").deleteMany({});
col("person").deleteMany({});
col("rideoffers").deleteMany({});
col("riderequests").deleteMany({});
col("rides").deleteMany({});
col("passengers").deleteMany({});


// coverage must match exactly: Haftpflicht | Teilkasko | Vollkasko
col("insurance").insertMany([
  {
    _id: "ins_1",
    name: "Allianz",
    policyName: "Basis Schutz",
    policyNumber: "AZ-123-456",
    coverage: "Haftpflicht",
    passengerDriverInsurance: true
  },
  {
    _id: "ins_2",
    name: "HUK-Coburg",
    policyName: "Komfort",
    policyNumber: "HUK-234-567",
    coverage: "Teilkasko",
    passengerDriverInsurance: true
  },
  {
    _id: "ins_3",
    name: "DEVK",
    policyName: "Premium",
    policyNumber: "DEVK-345-678",
    coverage: "Vollkasko",
    passengerDriverInsurance: true
  }
]);

col("car").insertMany([
  {
    _id: "car_1",
    make: "Volkswagen",
    model: "Golf",
    availableSeats: 4,
    luggageSpace: 3,
    smokingAllowed: false,
    petsAllowed: true,
    plate: "UL-AB-1234",
    buildYear: 2019,
    insuranceId: "ins_1"
  },
  {
    _id: "car_2",
    make: "BMW",
    model: "320i",
    availableSeats: 3,
    luggageSpace: 2,
    smokingAllowed: false,
    petsAllowed: false,
    plate: "UL-CD-5678",
    buildYear: 2021,
    insuranceId: "ins_2"
  },
  {
    _id: "car_3",
    make: "Mercedes-Benz",
    model: "C200",
    availableSeats: 4,
    luggageSpace: 4,
    smokingAllowed: true,
    petsAllowed: true,
    plate: "NU-EF-9012",
    buildYear: 2018,
    insuranceId: "ins_3"
  }
]);

col("person").insertMany([
  // Drivers
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
    carId: "car_1",
    languages: ["DE", "EN"],
    chatinessLevel: 2,
    overallKmCovered: 42000
  },
  {
    _id: "person_driver_2",
    name: "Sophie Becker",
    gender: "female",
    age: 31,
    homeCity: "Ulm",
    bio: "No smoking, no pets. I like punctuality.",
    phoneNumber: "+49 170 1111111",
    email: "sophie@example.com",
    street: "Donauweg 12",
    postalCode: "89073",
    preferredTransmission: ["MANUAL", "AUTOMATIC"],
    preferredCarType: ["SEDAN"],
    preferredMusic: ["ROCK", "INDIE"],
    carId: "car_2",
    languages: ["DE", "EN"],
    chatinessLevel: 1,
    overallKmCovered: 68000
  },
  {
    _id: "person_driver_3",
    name: "Ali Hassan",
    gender: "male",
    age: 34,
    homeCity: "Neu-Ulm",
    bio: "Friendly driver, flexible pickup if on the route.",
    phoneNumber: "+49 170 2222222",
    email: "ali@example.com",
    street: "Ringstraße 5",
    postalCode: "89233",
    preferredTransmission: ["AUTOMATIC"],
    preferredCarType: ["SEDAN", "WAGON"],
    preferredMusic: ["HIPHOP", "POP"],
    carId: "car_3",
    languages: ["DE", "EN", "AR"],
    chatinessLevel: 3,
    overallKmCovered: 95000
  },

  { _id: "person_passenger_1",  name: "Anna Müller",   gender: "female", age: 25, homeCity: "Ulm",     bio: "Student.", phoneNumber: "+49 170 3000001", email: "anna@example.com",  street: "Bergstraße 3",    postalCode: "89075", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE"],       chatinessLevel: 2, overallKmCovered: 8000 },
  { _id: "person_passenger_2",  name: "Jonas Klein",   gender: "male",   age: 27, homeCity: "Neu-Ulm", bio: "One suitcase.", phoneNumber: "+49 170 3000002", email: "jonas@example.com", street: "Wiesenweg 9",     postalCode: "89231", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 1, overallKmCovered: 12000 },
  { _id: "person_passenger_3",  name: "Laura Schmidt", gender: "female", age: 30, homeCity: "Ulm",     bio: "Quiet passenger.", phoneNumber: "+49 170 3000003", email: "laura@example.com", street: "Gartenweg 2",    postalCode: "89073", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 0, overallKmCovered: 25000 },
  { _id: "person_passenger_4",  name: "Peter Wagner",  gender: "male",   age: 33, homeCity: "Stuttgart", bio: "Business travel.", phoneNumber: "+49 170 3000004", email: "peter@example.com", street: "Parkallee 7",   postalCode: "70173", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 2, overallKmCovered: 40000 },
  { _id: "person_passenger_5",  name: "Mira Fischer",  gender: "female", age: 24, homeCity: "Munich",  bio: "Sometimes with pet.", phoneNumber: "+49 170 3000005", email: "mira@example.com",  street: "Sonnenstraße 1", postalCode: "80331", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 3, overallKmCovered: 9000 },
  { _id: "person_passenger_6",  name: "Tim Bauer",     gender: "male",   age: 28, homeCity: "Ulm",     bio: "One bag.", phoneNumber: "+49 170 3000006", email: "tim@example.com",   street: "Donaustraße 10", postalCode: "89073", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE"],       chatinessLevel: 2, overallKmCovered: 15000 },
  { _id: "person_passenger_7",  name: "Sara Nguyen",   gender: "female", age: 26, homeCity: "Neu-Ulm", bio: "Prefers calm rides.", phoneNumber: "+49 170 3000007", email: "sara@example.com",  street: "Marktplatz 4",  postalCode: "89231", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 1, overallKmCovered: 7000 },
  { _id: "person_passenger_8",  name: "David Meier",   gender: "male",   age: 35, homeCity: "Berlin",  bio: "Two bags often.", phoneNumber: "+49 170 3000008", email: "david@example.com", street: "Hauptstraße 20", postalCode: "10115", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN"],  chatinessLevel: 2, overallKmCovered: 52000 },
  { _id: "person_passenger_9",  name: "Elena Rossi",   gender: "female", age: 29, homeCity: "Ulm",     bio: "Weekend traveler.", phoneNumber: "+49 170 3000009", email: "elena@example.com", street: "Kastanienweg 6", postalCode: "89075", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE","EN","IT"], chatinessLevel: 3, overallKmCovered: 31000 },
  { _id: "person_passenger_10", name: "Felix Hartmann",gender: "male",   age: 23, homeCity: "Neu-Ulm", bio: "No luggage mostly.", phoneNumber: "+49 170 3000010", email: "felix@example.com", street: "Schillerstraße 8", postalCode: "89233", preferredTransmission: [], preferredCarType: [], preferredMusic: [], carId: null, languages: ["DE"],       chatinessLevel: 2, overallKmCovered: 4000 }
]);

col("rideoffers").insertMany([
  { _id: "offer_1", departureCity: "Neu-Ulm",  destinationCity: "Munich",    departureTime: new Date("2026-01-15T08:30:00Z"), seatsAvailable: 3, pricePerPerson: 18.5, luggageCount: 3, driverPersonId: "person_driver_1" },
  { _id: "offer_2", departureCity: "Ulm",      destinationCity: "Stuttgart", departureTime: new Date("2026-01-16T13:00:00Z"), seatsAvailable: 2, pricePerPerson: 22.0, luggageCount: 2, driverPersonId: "person_driver_1" },
  { _id: "offer_3", departureCity: "Ulm",      destinationCity: "Munich",    departureTime: new Date("2026-01-17T07:15:00Z"), seatsAvailable: 2, pricePerPerson: 20.0, luggageCount: 2, driverPersonId: "person_driver_2" },
  { _id: "offer_4", departureCity: "Neu-Ulm",  destinationCity: "Berlin",    departureTime: new Date("2026-01-18T06:45:00Z"), seatsAvailable: 3, pricePerPerson: 35.0, luggageCount: 4, driverPersonId: "person_driver_3" },
  { _id: "offer_5", departureCity: "Stuttgart",destinationCity: "Ulm",       departureTime: new Date("2026-01-19T17:30:00Z"), seatsAvailable: 2, pricePerPerson: 15.0, luggageCount: 2, driverPersonId: "person_driver_2" }
]);

print("Seed complete.");
printjson({
  insurance: col("insurance").countDocuments(),
  car: col("car").countDocuments(),
  person: col("person").countDocuments(),
  rideoffers: col("rideoffers").countDocuments()
});