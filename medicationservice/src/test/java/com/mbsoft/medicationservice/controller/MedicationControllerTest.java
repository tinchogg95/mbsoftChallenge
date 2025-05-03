package com.mbsoft.medicationservice.controller;

import com.mbsoft.medicationservice.controllers.MedicationController;
import com.mbsoft.medicationservice.entities.Medication;
import com.mbsoft.medicationservice.entities.MedicationType;
import com.mbsoft.medicationservice.repositories.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicationController.class)
class MedicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationRepository repository;

    @Test
    void createMedication_ShouldReturnCreatedMedication() throws Exception {
        Medication medication = new Medication();
        medication.setCode(1L);
        medication.setCommercialName("Ibuprofen");
        medication.setDrugName("Analgésico");

        when(repository.save(any(Medication.class))).thenReturn(medication);

        mockMvc.perform(post("/api/medications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commercialName\":\"Ibuprofen\",\"drugName\":\"Analgésico\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1)))
                .andExpect(jsonPath("$.commercialName", is("Ibuprofen")))
                .andExpect(jsonPath("$.drugName", is("Analgésico")));

        verify(repository, times(1)).save(any(Medication.class));
    }

    @Test
    void getByType_ShouldReturnMedications() throws Exception {
        MedicationType type = new MedicationType();
        type.setId(1L);
        type.setName("Analgésico");

        Medication medication = new Medication();
        medication.setCode(1L);
        medication.setCommercialName("Ibuprofen");
        medication.setDrugName("Antiinflamatorio");
        medication.setType(type);

        when(repository.findByTypeName("Analgésico")).thenReturn(List.of(medication));

        mockMvc.perform(get("/api/medications/by-type/Analgésico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is(1)))
                .andExpect(jsonPath("$[0].commercialName", is("Ibuprofen")))
                .andExpect(jsonPath("$[0].type.name", is("Analgésico")));

        verify(repository, times(1)).findByTypeName("Analgésico");
    }

    @Test
    void getByPrefix_ShouldReturnFilteredMedications() throws Exception {
        Medication medication = new Medication();
        medication.setCode(1L);
        medication.setCommercialName("Ibuprofeno");
        medication.setDrugName("Antiinflamatorio");

        when(repository.findByCommercialNameStartingWithIgnoreCase("Ib")).thenReturn(List.of(medication));

        mockMvc.perform(get("/api/medications/by-prefix/Ib"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].commercialName", is("Ibuprofeno")));

        verify(repository, times(1)).findByCommercialNameStartingWithIgnoreCase("Ib");
    }

    @Test
    void getByPrefix_ShouldHandleEmptyResults() throws Exception {
        when(repository.findByCommercialNameStartingWithIgnoreCase("Xy")).thenReturn(List.of());

        mockMvc.perform(get("/api/medications/by-prefix/Xy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(repository, times(1)).findByCommercialNameStartingWithIgnoreCase("Xy");
    }
}