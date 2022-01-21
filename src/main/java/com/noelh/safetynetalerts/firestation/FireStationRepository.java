package com.noelh.safetynetalerts.firestation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FireStation Repository
 */
@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
