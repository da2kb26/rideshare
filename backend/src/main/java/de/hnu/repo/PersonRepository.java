package de.hnu.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.hnu.domain.Person;

public interface PersonRepository extends MongoRepository<Person, String> {}