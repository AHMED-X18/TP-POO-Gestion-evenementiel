package com.example.Gestion.d.evenements.models;

import com.example.Gestion.d.evenements.Observer.EvenementObservable;
import com.example.Gestion.d.evenements.Observer.ParticipantObserver;
import com.example.Gestion.d.evenements.exceptions.CapaciteMaxAtteinteException;
import lombok.*;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "concert")
})
@Getter
@Setter
public abstract class Evenement implements EvenementObservable {
    protected String id;
    protected String nom;
    protected LocalDateTime date;
    protected String lieu;
    protected int capaciteMax;
    @JsonIgnore
    protected List<Participant> participants = new ArrayList<>();
    @JsonIgnore
    protected List<ParticipantObserver> observers = new ArrayList<>();
    protected List<String> participantIds = new ArrayList<>();

    public void ajouterParticipant(Participant participant) throws CapaciteMaxAtteinteException {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement " + nom);
        }
        if (!participants.contains(participant)) {
            participants.add(participant);
            participantIds.add(participant.getId());
            ajouterObserver(participant);
        }
    }

    public void retirerParticipant(Participant participant) {
        participants.remove(participant);
        participantIds.remove(participant.getId());
        retirerObserver(participant);
    }

    public void annuler() {
        notifierObservers("L'événement " + nom + " a été annulé");
    }

    public abstract void afficherDetails();

    // Observer Pattern Implementation
    @Override
    public void ajouterObserver(ParticipantObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void retirerObserver(ParticipantObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifierObservers(String message) {
        for (ParticipantObserver observer : observers) {
            observer.mettreAJour(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return Objects.equals(id, evenement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}