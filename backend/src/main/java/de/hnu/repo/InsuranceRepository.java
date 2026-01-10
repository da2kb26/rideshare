package de.hnu.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.hnu.domain.Insurance;

public interface InsuranceRepository extends MongoRepository<Insurance, String> {}