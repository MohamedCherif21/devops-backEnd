package com.example.cocomarket.repository;


import com.example.cocomarket.entity.CART;
import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.entity.ProduitCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitCartrepository extends JpaRepository<ProduitCart, Integer> {


    @Query("SELECT pc FROM ProduitCart pc WHERE pc.cart = :cart AND pc.produit = :produit")
    Optional<ProduitCart> findByCartAndProduit(@Param("cart") CART cart, @Param("produit") Produit produit);



    @Query("SELECT pc FROM ProduitCart pc WHERE pc.cart = :cart ")
    Optional<ProduitCart> findByCart(@Param("cart") CART cart);

}
