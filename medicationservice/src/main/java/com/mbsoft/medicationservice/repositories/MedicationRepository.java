package com.mbsoft.medicationservice.repositories;

import com.mbsoft.medicationservice.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByTypeName(String typeName);
    List<Medication> findByCommercialNameStartingWithIgnoreCase(String prefix);
}