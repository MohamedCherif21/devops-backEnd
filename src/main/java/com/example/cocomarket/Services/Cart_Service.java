package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.CART;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.ProduitQuantiteDTO;
import com.example.cocomarket.Entity.Produit_Cart;
import com.example.cocomarket.Interfaces.ICart;
import com.example.cocomarket.Repository.Cart_Repository;
import com.example.cocomarket.Repository.Produit_Cart_repository;
import com.example.cocomarket.Repository.Produit__Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;


@Service
public class Cart_Service implements ICart {

    @Autowired
    Produit__Repository pr;

    @Autowired
    Cart_Repository cr;

    @Autowired
    static
    Produit_Cart_repository pcr;

    /*@Autowired
    private APIContext apiContext ;*/


    @Override
    public CART Add_Product_To_Cart(Integer idProduit, Integer idCart) {

        CART cart = cr.findById(idCart).orElseThrow(() -> new NotFoundException("Cart not found"));
        Produit product = pr.getById(idProduit);
        Produit_Cart cartItem = cart.getItems().stream()
                .filter(item -> item.getProduit().getId().equals(idProduit))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            // Add new item to cart
            cartItem = new Produit_Cart(product, 1);
            cart.getItems().add(cartItem);
            cartItem.setCart(cart);
            cartItem.setProduit(product);
            cartItem.setQuantity(1);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cart.setNbProd(cart.getNbProd() + 1);
        cart.setTotal_price(cart.getTotal_price() + product.getPrix());
        cart.setTotal_weight(cart.getTotal_weight() + product.getWeight());
        cart.setTotal_volume(cart.getTotal_volume()+product.getVolume());
        return cr.save(cart);
    }

    @Override
    public void Remove_Product(Integer idProduit, Integer idCart) {
        // Récupère le panier correspondant à l'identifiant donné
        CART cart = cr.findById(idCart).orElse(null);

        // Si le panier n'existe pas ou est vide, arrête la méthode
        if (cart == null || cart.getItems().isEmpty()) {
            return;
        }

        // Recherche l'élément correspondant au produit donné dans le panier
        Produit_Cart cartItem = null;
        for (Produit_Cart item : cart.getItems()) {
            if (item.getProduit().getId().equals(idProduit)) {
                cartItem = item;
                // Si la quantité du produit dans le panier est de 1, retire l'élément du panier et de la base de données
                if (cartItem.getQuantity() == 0) {
                    cart.getItems().remove(item);
                    pcr.delete(cartItem);
                } else {
                    // Sinon, diminue la quantité du produit de 1
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
                break;
            }
        }

        // Si l'élément correspondant au produit n'est pas trouvé, arrête la méthode
        if (cartItem == null) {
            return;
        }



        // Récupère le produit associé à l'élément du panier retiré
        Produit produit = cartItem.getProduit();

        // Diminue le nombre total de produits, le prix total et le poids total du panier
        if (cart.getNbProd() > 0) {
            cart.setNbProd(cart.getNbProd() - 1);
            cart.setTotal_price(cart.getTotal_price() - produit.getPrix());
            cart.setTotal_weight(cart.getTotal_weight() - produit.getWeight());
            cart.setTotal_volume(cart.getTotal_volume() - produit.getVolume());
        }

        // Enregistre les modifications du panier dans la base de données
        cr.save(cart);
    }



    @Override
    public Produit retrive_one_Product(Integer idprodCart, Integer idProduit) {
        Produit_Cart produit_cart = pcr.findById(idprodCart).get();
        Produit product = produit_cart.getProduit();
        if (product.getId().equals(idProduit)) {
            System.out.println("equal"+product);
            return product;
        }
        System.out.println("not equal"+product.getId());
        return null ;
    }

   /* public Produit retrive_one_Product(Integer idProduit) {
        Produit_Cart produit_cart = pcr.findById(idProduit).get();
        Produit product = produit_cart.getProduit();
        return product;
    }*/

    @Override
    public List<Produit> retrieveAllProductInCart(Integer cartId) {
        Optional<CART> cartOptional = cr.findById(cartId);
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("Cart not found with id " + cartId);
        }
        CART cart = cartOptional.get();
        List<Produit> produits = new ArrayList<>();
        for (Produit_Cart produitCart : cart.getItems()) {
            produits.add(produitCart.getProduit());
        }
        return produits;
    }

   /* public Map<Integer, Integer> getProductQuantitiesByCartId1(Integer cartId) {
        List<Produit_Cart> productCarts = pcr.findById(cartId);
        Map<Integer, Integer> productQuantities = new HashMap<>();
        for (Produit_Cart productCart : productCarts) {
            productQuantities.put(productCart.getProduit().getId(), productCart.getQuantity());
        }
        return productQuantities;
    }


    public static Map<Integer, Integer> getProductQuantitiesByCartId(Integer cartId) {
        Optional<Produit_Cart> optionalProductCarts = pcr.findById(cartId);
        if (optionalProductCarts.isPresent()) {
            List<Produit_Cart> productCarts = (List<Produit_Cart>) optionalProductCarts.get();
            Map<Integer, Integer> productQuantities = new HashMap<>();
            for (Produit_Cart productCart : productCarts) {
                productQuantities.put(productCart.getProduit().getId(), productCart.getQuantity());
            }
            return productQuantities;
        } else {
            // Le panier n'a aucun produit
            return Collections.emptyMap();
        }
    }*/

    public static Map<Integer, Integer> getProductQuantitiesByCartId(Integer cartId) {
        Optional<Produit_Cart> optionalProductCarts = pcr.findById(cartId);
        Map<Integer, Integer> productQuantities = new HashMap<>();
        if (optionalProductCarts.isPresent()) {
            List<Produit_Cart> productCarts = (List<Produit_Cart>) optionalProductCarts.get();
            for (Produit_Cart productCart : productCarts) {
                productQuantities.put(productCart.getProduit().getId(), productCart.getQuantity());
            }
        }
        return productQuantities;
    }



}




