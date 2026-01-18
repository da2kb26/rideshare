package de.hnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, String> {}