package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.CART;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Produit_Cart;
import com.example.cocomarket.Repository.Cart_Repository;
import com.example.cocomarket.Repository.Produit_Cart_repository;
import com.example.cocomarket.Repository.Produit__Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Produit_Cart_Service {



    @Autowired
    private Produit_Cart_repository produitCartRepository;

    @Autowired
    Cart_Repository cr ;

    @Autowired
    Produit__Repository pr ;


    public Integer getQuantityByCartIdAndProduitId(Integer cartId, Integer produitId) {
        CART cart = cr.findById(cartId).orElseThrow(null);
        Produit produit = pr.findById(produitId).orElseThrow(null);
        Optional<Produit_Cart> produitCart = produitCartRepository.findByCartAndProduit(cart, produit);
        return produitCart.map(Produit_Cart::getQuantity).orElse(0);
    }

    public void deleteProduitById(Integer cartId, Integer produitId) {
        CART cart = cr.findById(cartId).orElseThrow(null);
        Produit produit = pr.findById(produitId).orElseThrow(null);
        Optional<Produit_Cart> produitCart = produitCartRepository.findByCartAndProduit(cart,produit);
        Integer quantity = produitCart.map(Produit_Cart::getQuantity).orElse(0);
        if(quantity == 0){
            produitCartRepository.delete(produitCart.get());
        }

    }
}
