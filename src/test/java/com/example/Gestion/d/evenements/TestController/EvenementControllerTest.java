package com.example.Gestion.d.evenements.TestController;

import com.example.Gestion.d.evenements.services.EvenementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EvenementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // No reset or delete; preserve existing events in evenements.json
        // Ensure file exists to avoid IOException in EvenementService
        try {
            Path path = Paths.get("src/main/resources/data/evenements.json");
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.writeString(path, "[]", StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize evenements.json", e);
        }
    }

    @Test
    void testCreateConferenceSuccess() throws Exception {
        mockMvc.perform(post("/evenements/creation")
                        .param("nom", "Test Conference")
                        .param("lieu", "Yaoundé")
                        .param("capaciteMax", "100")
                        .param("theme", "AI")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Test Conference"))
                .andExpect(jsonPath("$.theme").value("AI"))
                .andExpect(jsonPath("$.date").value("2025-05-24T20:24"))
                .andExpect(jsonPath("$.participants").isArray())
                .andExpect(jsonPath("$.participants").isEmpty());
    }

    @Test
    void testCreateConferenceInvalidDate() throws Exception {
        mockMvc.perform(post("/evenements/creation")
                        .param("nom", "Test Conference")
                        .param("lieu", "Yaoundé")
                        .param("capaciteMax", "100")
                        .param("theme", "AI")
                        .param("date", "24-05-2025 T 16:03:00")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Format de date invalide. Utilisez yyyy-MM-dd'T'HH:mm:ss ou laissez vide pour utiliser la date actuelle"));
    }

    @Test
    void testGetAllEvenements() throws Exception {
        // Create an event first
        mockMvc.perform(post("/evenements/creation")
                        .param("nom", "Another Conference")
                        .param("lieu", "Douala")
                        .param("capaciteMax", "50")
                        .param("theme", "Tech")
                        .param("date", "2025-05-24T16:03:00")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        mockMvc.perform(get("/evenements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.nom=='Another Conference')]").exists());
    }
}