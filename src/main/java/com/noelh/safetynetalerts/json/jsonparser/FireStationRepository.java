package com.noelh.safetynetalerts.json.jsonparser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
