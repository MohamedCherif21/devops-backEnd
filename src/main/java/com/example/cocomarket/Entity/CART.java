package com.example.cocomarket.Entity;

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
    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "table_associative",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Produit> items;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonIgnore
    private Set<Produit_Cart> Items;


    @OneToMany (mappedBy = "Commande_cart")
    private Set<Commande> commande_cart;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user ;


}
