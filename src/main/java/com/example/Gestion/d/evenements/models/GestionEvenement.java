package com.example.Gestion.d.evenements.models;

import com.example.Gestion.d.evenements.exceptions.CapaciteMaxAtteinteException;
import com.example.Gestion.d.evenements.exceptions.EvenementDejaExistantException;
import lombok.*;

import java.util.Map;

@Getter
@Setter
public class GestionEvenement {
    private Map<String, Evenement> evenements;

    public void ajouterEvenement(Evenement e) throws EvenementDejaExistantException {
        if (evenements.containsKey(e.getId())) {
            throw new EvenementDejaExistantException("L'événement existe déjà !");
        }
        this.evenements.put(e.getId(),e);
    }
    public void supprimerEvenement(){}
    public void rechercherEvenement(){}

    public void ajouterParticipant(Evenement event, Participant p) throws CapaciteMaxAtteinteException {
        if (event.getParticipants().size() >= event.getCapaciteMax()) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte !");
        }
        event.ajouterParticipant(p);
    }

    private GestionEvenement(Map<String, Evenement> evenements){
        this.evenements = evenements;
    }

}
