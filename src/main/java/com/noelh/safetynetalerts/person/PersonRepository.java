package com.noelh.safetynetalerts.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Person Repository
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
