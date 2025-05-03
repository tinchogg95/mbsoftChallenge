package com.mbsoft.medicationservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Medication {
    @Id
    private Long code;
    
    private String commercialName;
    private String drugName;
    
    @ManyToOne
    @JoinColumn(name = "type_id")
    private MedicationType type;

    public Medication(Long code, String commercialName, String drugName) {
        this.code = code;
        this.commercialName = commercialName;
        this.drugName = drugName;
    }
}