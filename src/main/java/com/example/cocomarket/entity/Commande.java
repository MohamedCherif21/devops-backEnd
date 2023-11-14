package com.example.cocomarket.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private int id;

    private LocalDateTime dateCmd;

    private String shopname;
    private String shopaddress;

    private String buyeremail;
    private String buyeraddress;

    private Long tax;
    private String lesproduits;

    private Integer nbProd;
    private Long totalprice;
    private String currency;
    private float totalweight;
    private Float sommevolume;

    private Boolean archive;

    private String description;

    @Enumerated(EnumType.STRING)
    private Etat etat;//1 bch twali livraison

    @Enumerated(EnumType.STRING)
    private Payment_Mode paymentmode;

    private  String methode ;



    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private CART commandeCart;

    @ManyToOne
    @JsonIgnore
    private Livraison livraisonCommande;


}
