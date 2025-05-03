package com.mbsoft.productcodeservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void isPriority_ShouldReturnCorrectValue() throws Exception {
        mockMvc.perform(get("/api/product-codes/P123-456-7/is-priority"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/api/product-codes/A123-456-7/is-priority"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void verify_ShouldValidateCodeCorrectly() throws Exception {
        mockMvc.perform(get("/api/product-codes/ABC-123-6/verify"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/api/product-codes/ABC-123-5/verify"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void sortProducts_ShouldSortCorrectly() throws Exception {
        List<String> productos = Arrays.asList("Z-1-1", "A-2-2", "B-3-3");
        
        mockMvc.perform(post("/api/product-codes/sort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("A-2-2")))
                .andExpect(jsonPath("$[1]", is("B-3-3")))
                .andExpect(jsonPath("$[2]", is("Z-1-1")));
    }

    @Test
    void union_ShouldCombineLists() throws Exception {
        List<List<String>> listas = Arrays.asList(
            Arrays.asList("A-1-1", "B-2-2"),
            Arrays.asList("B-2-2", "C-3-3")
        );
        
        mockMvc.perform(post("/api/product-codes/union")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(listas)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$", containsInAnyOrder("A-1-1", "B-2-2", "C-3-3")));
    }

    @Test
    void intersection_ShouldFindCommonElements() throws Exception {
        List<List<String>> listas = Arrays.asList(
            Arrays.asList("A-1-1", "B-2-2", "C-3-3"),
            Arrays.asList("B-2-2", "C-3-3", "D-4-4")
        );
        
        mockMvc.perform(post("/api/product-codes/intersection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(listas)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder("B-2-2", "C-3-3")));
    }

    // Método auxiliar para convertir objetos a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}