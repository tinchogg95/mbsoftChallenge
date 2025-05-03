package com.mbsoft.medicationservice.controllers;

import com.mbsoft.medicationservice.entities.MedicationType;
import com.mbsoft.medicationservice.repositories.MedicationTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication-types")
public class MedicationTypeController {

    private final MedicationTypeRepository repository;

    public MedicationTypeController(MedicationTypeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public MedicationType create(@RequestBody MedicationType medicationType) {
        return repository.save(medicationType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.findById(id).ifPresent(type -> {
            type.setActive(false);
            repository.save(type);
        });
    }

    @GetMapping
    public List<MedicationType> getAll() {
        return repository.findByActiveTrue();
    }
}