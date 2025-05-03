package com.mbsoft.medicationservice.controller;

import com.mbsoft.medicationservice.entities.MedicationType;
import com.mbsoft.medicationservice.repositories.MedicationTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicationTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationTypeRepository repository;

    @Test
    void createMedicationType_ShouldReturnCreatedType() throws Exception {
        MedicationType type = new MedicationType(1L, "Analgésico", true);
        when(repository.save(any(MedicationType.class))).thenReturn(type);

        mockMvc.perform(post("/api/medication-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Analgésico\",\"active\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Analgésico")))
                .andExpect(jsonPath("$.active", is(true)));

        verify(repository, times(1)).save(any(MedicationType.class));
    }

    @Test
    void deleteMedicationType_ShouldReturnNoContent() throws Exception {
        MedicationType type = new MedicationType(1L, "Analgésico", true);
        when(repository.findById(1L)).thenReturn(Optional.of(type));
        when(repository.save(any(MedicationType.class))).thenReturn(type);

        mockMvc.perform(delete("/api/medication-types/1"))
                .andExpect(status().isOk());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(MedicationType.class));
    }

    @Test
    void deleteNonExistentType_ShouldReturnOk() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/medication-types/1"))
                .andExpect(status().isOk());

        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void getAllActiveTypes_ShouldReturnList() throws Exception {
        List<MedicationType> types = Arrays.asList(
            new MedicationType(1L, "Analgésico", true),
            new MedicationType(2L, "Antiinflamatorio", true)
        );
        when(repository.findByActiveTrue()).thenReturn(types);

        mockMvc.perform(get("/api/medication-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Analgésico")))
                .andExpect(jsonPath("$[1].name", is("Antiinflamatorio")));

        verify(repository, times(1)).findByActiveTrue();
    }
}