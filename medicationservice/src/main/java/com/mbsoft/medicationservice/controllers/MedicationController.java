package com.mbsoft.medicationservice.controllers;

import com.mbsoft.medicationservice.entities.Medication;
import com.mbsoft.medicationservice.repositories.MedicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationRepository repository;

    public MedicationController(MedicationRepository repository) {
        this.repository = repository;
    }

    // Create new medication
    @PostMapping
    public Medication create(@RequestBody Medication medication) {
        return repository.save(medication);
    }

    // List aerosol medications
    @GetMapping("/by-type/{typeName}")
    public List<Medication> getByType(@PathVariable String typeName) {
        return repository.findByTypeName(typeName);
    }

    // List medications starting with letter
    @GetMapping("/by-prefix/{prefix}")
    public List<Medication> getByPrefix(@PathVariable String prefix) {
        return repository.findByCommercialNameStartingWithIgnoreCase(prefix);
    }
}