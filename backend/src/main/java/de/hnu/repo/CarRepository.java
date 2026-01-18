package de.hnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.Car;

public interface CarRepository extends JpaRepository<Car, String> {}