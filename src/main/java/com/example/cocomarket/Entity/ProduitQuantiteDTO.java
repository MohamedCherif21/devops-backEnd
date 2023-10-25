package com.example.cocomarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProduitQuantiteDTO {
    private Integer id;
    private String nom;
    private Long prix;
    private Integer quantite;
}

