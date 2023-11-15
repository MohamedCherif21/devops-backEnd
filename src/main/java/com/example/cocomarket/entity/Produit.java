package com.example.cocomarket.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @NonNull
    private Integer id;

    private String reference;

    @NonNull
    private String nom;


    private String img;
    private String description;
    @NonNull
    private Long prix;
    @NonNull
    private Float weight;
    @NonNull
    private Float volume ;


    private Boolean etatsProduit;//mawjoud ou non
    @Enumerated(EnumType.STRING)
    private Status status;//ywefe9 3lih lbaye3 bch ybi3ou ou non//par default Null
    private LocalDate datePublication;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Raiting_Product> raitingproducts;
    @OneToMany(mappedBy = "produit",cascade = CascadeType.ALL)
    private Set<ImageData> image ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Categorie categories;

    @ManyToOne
    @JsonIgnore
    private Shop shopes;

    @ManyToMany(mappedBy = "produits")
    @JsonIgnore
    private Set<Catalogue> catalogues = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produit")
    @JsonIgnore
    private Set<ProduitCart> items;

    private Integer quantiteVendue;

    private Integer pourcentagePromotion;

    private Boolean isFlashSale;
    private Boolean isPromotion;
    private Float prixPromotion;



}
