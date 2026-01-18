package de.hnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hnu.data.Person;

public interface PersonRepository extends JpaRepository<Person, String> {}