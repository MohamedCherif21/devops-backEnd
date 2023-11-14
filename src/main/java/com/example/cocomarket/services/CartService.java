package com.example.cocomarket.services;

import com.example.cocomarket.entity.CART;
import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.entity.ProduitCart;
import com.example.cocomarket.interfaces.ICart;
import com.example.cocomarket.repository.CartRepository;
import com.example.cocomarket.repository.ProduitCartrepository;
import com.example.cocomarket.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;


@Service
public class CartService implements ICart {

    @Autowired
    ProduitRepository pr;

    @Autowired
    CartRepository cr;

    @Autowired
    static
    ProduitCartrepository pcr;

    @Override
    public CART addProductToCart(Integer idProduit, Integer idCart) {

        CART cart = cr.findById(idCart).orElseThrow(() -> new NotFoundException("Cart not found"));
        Produit product = pr.getById(idProduit);
        ProduitCart cartItem = cart.getItems().stream()
                .filter(item -> item.getProduit().getId().equals(idProduit))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            // Add new item to cart
            cartItem = new ProduitCart(product, 1);
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
    public void removeProduct(Integer idProduit, Integer idCart) {
        // Récupère le panier correspondant à l'identifiant donné
        CART cart = cr.findById(idCart).orElse(null);

        // Si le panier n'existe pas ou est vide, arrête la méthode
        if (cart == null || cart.getItems().isEmpty()) {
            return;
        }

        // Recherche l'élément correspondant au produit donné dans le panier
        ProduitCart cartItem = null;
        for (ProduitCart item : cart.getItems()) {
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
    public Produit retriveOneProduct(Integer idprodCart, Integer idProduit) {
        ProduitCart produitCart = pcr.findById(idprodCart).get();
        Produit product = produitCart.getProduit();
        if (product.getId().equals(idProduit)) {
            return product;
        }
        return null ;
    }

    @Override
    public List<Produit> retrieveAllProductInCart(Integer cartId) {
        Optional<CART> cartOptional = cr.findById(cartId);
        if (!cartOptional.isPresent()) {
            throw new NullPointerException("Cart not found with id " + cartId);
        }
        CART cart = cartOptional.get();
        List<Produit> produits = new ArrayList<>();
        for (ProduitCart produitCart : cart.getItems()) {
            produits.add(produitCart.getProduit());
        }
        return produits;
    }

    public static Map<Integer, Integer> getProductQuantitiesByCartId(Integer cartId) {
        Optional<ProduitCart> optionalProductCarts = pcr.findById(cartId);
        Map<Integer, Integer> productQuantities = new HashMap<>();
        if (optionalProductCarts.isPresent()) {
            List<ProduitCart> productCarts = (List<ProduitCart>) optionalProductCarts.get();
            for (ProduitCart productCart : productCarts) {
                productQuantities.put(productCart.getProduit().getId(), productCart.getQuantity());
            }
        }
        return productQuantities;
    }



}




