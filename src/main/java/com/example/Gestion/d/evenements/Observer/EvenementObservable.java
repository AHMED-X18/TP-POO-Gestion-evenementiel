package com.example.Gestion.d.evenements.Observer;

public interface EvenementObservable {
        void ajouterObserver(ParticipantObserver observer);
        void retirerObserver(ParticipantObserver observer);
        void notifierObservers(String message);
}