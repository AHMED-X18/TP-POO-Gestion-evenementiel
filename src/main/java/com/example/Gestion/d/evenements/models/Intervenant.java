package com.example.Gestion.d.evenements.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Intervenant {
    private String id;
    private String nom;
    private String specialite;
}