package com.mbsoft.medicationservice.repositories;

import com.mbsoft.medicationservice.entities.MedicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationTypeRepository extends JpaRepository<MedicationType, Long> {
    List<MedicationType> findByActiveTrue();
}