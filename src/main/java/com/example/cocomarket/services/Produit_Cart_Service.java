package com.example.cocomarket.services;

import com.example.cocomarket.entity.CART;
import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.entity.ProduitCart;
import com.example.cocomarket.repository.CartRepository;
import com.example.cocomarket.repository.ProduitCartrepository;
import com.example.cocomarket.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Produit_Cart_Service {



    @Autowired
    private ProduitCartrepository produitCartRepository;

    @Autowired
    CartRepository cr ;

    @Autowired
    ProduitRepository pr ;


    public Integer getQuantityByCartIdAndProduitId(Integer cartId, Integer produitId) {
        CART cart = cr.findById(cartId).orElseThrow(null);
        Produit produit = pr.findById(produitId).orElseThrow(null);
        Optional<ProduitCart> produitCart = produitCartRepository.findByCartAndProduit(cart, produit);
        return produitCart.map(ProduitCart::getQuantity).orElse(0);
    }

    public void deleteProduitById(Integer cartId, Integer produitId) {
        CART cart = cr.findById(cartId).orElseThrow(null);
        Produit produit = pr.findById(produitId).orElseThrow(null);
        Optional<ProduitCart> produitCart = produitCartRepository.findByCartAndProduit(cart,produit);
        Integer quantity = produitCart.map(ProduitCart::getQuantity).orElse(0);
        if(quantity == 0){
            produitCartRepository.delete(produitCart.get());
        }

    }
}
