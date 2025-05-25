package com.example.Gestion.d.evenements.models;

import com.example.Gestion.d.evenements.Observer.ParticipantObserver;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participant implements ParticipantObserver {
    protected String id;
    protected String nom;
    protected String email;
    protected List<String> evenementIds = new ArrayList<>();

    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.evenementIds = new ArrayList<>();
    }

    @Override
    public void mettreAJour(String message) {
        System.out.println("📧 Notification pour " + nom + " (" + email + "): " + message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}