package com.example.Gestion.d.evenements.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Concert extends Evenement {
    private String artiste;
    private String genreMusical;

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONCERT ===");
        System.out.println("Nom: " + nom);
        System.out.println("Artiste: " + artiste);
        System.out.println("Genre: " + genreMusical);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Participants: " + participants.size() + "/" + capaciteMax);
        System.out.println("===============");
    }
}
