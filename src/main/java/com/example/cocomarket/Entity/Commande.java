package com.example.cocomarket.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class Commande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private int id;

    private LocalDateTime dateCmd;

    private String shop_name;
    private String shop_address;

    private String buyer_email;
    private String buyer_address;

    private Long tax;
    private String les_produits ;

    private Integer nbProd;
    private Long total_price;
    private String currency;
    private float total_weight ;
    private Float somme_volume;

    private Boolean archive;

    private String description;

    @Enumerated(EnumType.STRING)
    private Etat etat;//1 bch twali livraison

    @Enumerated(EnumType.STRING)
    private Payment_Mode payment_mode;

    private  String methode ;



    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private CART Commande_cart;

    @ManyToOne
    @JsonIgnore
    private Livraison Livraison_commande;


}
