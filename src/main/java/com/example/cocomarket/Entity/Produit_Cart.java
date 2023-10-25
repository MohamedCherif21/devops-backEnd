package com.example.cocomarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor


public class Produit_Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer quantity;
    
    @ManyToOne
    private CART cart ;

    @ManyToOne
    private Produit produit ;


    public Produit_Cart(Produit product, int i) {
    }
}
