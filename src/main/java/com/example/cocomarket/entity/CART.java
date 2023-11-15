package com.example.cocomarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CART implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private  Integer id;
    private Integer nbProd;
    private Long total_price;
    private Float total_weight;
    private Float total_volume ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonIgnore
    private transient Set<ProduitCart> Items;



    @OneToMany(mappedBy = "Commande_cart")
    private transient Set<Commande> commande_cart;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user ;


    public CART(int i, int i1, double v, double v1, double v2) {
    }
}
