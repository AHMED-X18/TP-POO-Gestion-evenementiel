package com.example.Gestion.d.evenements.services;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {
    void envoyerNotification(String message);
    CompletableFuture<Void> envoyerNotificationAsync(String message);
}