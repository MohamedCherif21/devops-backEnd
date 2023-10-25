package com.example.cocomarket.Repository;


import com.example.cocomarket.Entity.CART;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Produit_Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Produit_Cart_repository extends JpaRepository<Produit_Cart, Integer> {


    @Query("SELECT pc FROM Produit_Cart pc WHERE pc.cart = :cart AND pc.produit = :produit")
    Optional<Produit_Cart> findByCartAndProduit(@Param("cart") CART cart, @Param("produit") Produit produit);



    @Query("SELECT pc FROM Produit_Cart pc WHERE pc.cart = :cart ")
    Optional<Produit_Cart> findByCart(@Param("cart") CART cart);

}
