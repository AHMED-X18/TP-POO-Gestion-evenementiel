package com.example.Gestion.d.evenements.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Organisateur extends Participant {
    private List<String> evenementsOrganises = new ArrayList<>();

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        this.evenementsOrganises = new ArrayList<>();
    }

    public void ajouterEvenementOrganise(String evenementId) {
        if (!evenementsOrganises.contains(evenementId)) {
            evenementsOrganises.add(evenementId);
        }
    }

    public void retirerEvenementOrganise(String evenementId) {
        evenementsOrganises.remove(evenementId);
    }
}