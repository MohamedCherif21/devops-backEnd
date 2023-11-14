package com.example.cocomarket.entity;

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


public class ProduitCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer quantity;
    
    @ManyToOne
    private CART cart ;

    @ManyToOne
    private Produit produit ;


    public ProduitCart(Produit product, int i) {
    }
}
