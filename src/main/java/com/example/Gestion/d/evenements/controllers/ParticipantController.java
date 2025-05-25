package com.example.Gestion.d.evenements.controllers;

import com.example.Gestion.d.evenements.exceptions.ParticipantNonTrouveException;
import com.example.Gestion.d.evenements.models.Participant;
import com.example.Gestion.d.evenements.services.ParticipantService;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@CrossOrigin(origins = "*")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<Participant>> obtenirTousLesParticipants() {
        return ResponseEntity.ok(participantService.obtenirTousLesParticipants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> obtenirParticipantParId(@PathVariable String id) {
        return participantService.obtenirParticipantParId(id)
                .map(participant -> ResponseEntity.ok(participant))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/creation")
    public ResponseEntity<?> creerParticipant(
            @RequestParam String nom,
            @RequestParam String email) throws ValidationException {
        Participant Newparticipant = participantService.creerParticipant(nom, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(Newparticipant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourParticipant(@PathVariable String id, @RequestBody Participant participant) {
        try {
            Participant participantMisAJour = participantService.mettreAJourParticipant(id, participant);
            return ResponseEntity.ok(participantMisAJour);
        } catch (ParticipantNonTrouveException | ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerParticipant(@PathVariable String id) {
        try {
            participantService.supprimerParticipant(id);
            return ResponseEntity.noContent().build();
        } catch (ParticipantNonTrouveException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Participant>> rechercherParticipants(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String email) {

        if (nom != null && !nom.isEmpty()) {
            return ResponseEntity.ok(participantService.rechercherParticipantsParNom(nom));
        }
        if (email != null && !email.isEmpty()) {
            return ResponseEntity.ok(participantService.rechercherParticipantsParEmail(email));
        }

        return ResponseEntity.ok(participantService.obtenirTousLesParticipants());
    }
}