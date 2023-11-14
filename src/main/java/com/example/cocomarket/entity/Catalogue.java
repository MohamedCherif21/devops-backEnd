package com.example.cocomarket.entity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Catalogue {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private  Integer id;
    private String nom;
    private  String description;
    private   String img;
    private byte[] qrCode;


    @ManyToMany
    @JoinTable(name = "catalogue_produit",
            joinColumns = @JoinColumn(name = "catalogue_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"))
    private Set<Produit> produits = new HashSet<>();


}
