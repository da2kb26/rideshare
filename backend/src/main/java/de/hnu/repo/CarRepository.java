package de.hnu.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.hnu.domain.Car;

public interface CarRepository extends MongoRepository<Car, String> {}