package com.example.Gestion.d.evenements.TestService;

import com.example.Gestion.d.evenements.models.Participant;
import com.example.Gestion.d.evenements.services.JsonDataService;
import com.example.Gestion.d.evenements.services.NotificationService;
import com.example.Gestion.d.evenements.services.ParticipantService;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ParticipantServiceTest {

    private ParticipantService participantService;
    private NotificationService notificationService;
    private JsonDataService jsonDataService;

    @BeforeEach
    void setUp() {
        jsonDataService = mock(JsonDataService.class);
        notificationService = mock(NotificationService.class);
        participantService = new ParticipantService(jsonDataService, notificationService);

        when(jsonDataService.chargerParticipants()).thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Doit créer un participant avec succès")
    void testCreerParticipant() throws Exception {
        // Given
        Participant participant = new Participant("P001", "John Doe", "john@example.com");

        // When
        Participant result = participantService.creerParticipant(participant);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getNom());
        assertEquals("john@example.com", result.getEmail());
        verify(jsonDataService).sauvegarderParticipants(any());
        verify(notificationService).envoyerNotificationAsync(anyString());
    }

    @Test
    @DisplayName("Doit lever une exception pour un email invalide")
    void testCreerParticipantEmailInvalide() {
        // Given
        Participant participant = new Participant("P001", "John Doe", "invalid-email");

        // When & Then
        assertThrows(ValidationException.class, () -> {
            participantService.creerParticipant(participant);
        });
    }

    @Test
    @DisplayName("Doit rechercher des participants par nom")
    void testRechercherParNom() throws Exception {
        // Given
        Participant p1 = new Participant("P001", "Alice Martin", "alice@example.com");
        Participant p2 = new Participant("P002", "Bob Martin", "bob@example.com");
        Participant p3 = new Participant("P003", "Charlie Smith", "charlie@example.com");

        participantService.creerParticipant(p1);
        participantService.creerParticipant(p2);
        participantService.creerParticipant(p3);

        // When
        List<Participant> resultats = participantService.rechercherParticipantsParNom("Martin");

        // Then
        assertEquals(2, resultats.size());
        assertTrue(resultats.stream().allMatch(p -> p.getNom().contains("Martin")));
    }
}
