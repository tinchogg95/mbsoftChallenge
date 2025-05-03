package com.mbsoft.medicationservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MedicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private boolean active = true;

    public MedicationType(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public MedicationType(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }
    public MedicationType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}