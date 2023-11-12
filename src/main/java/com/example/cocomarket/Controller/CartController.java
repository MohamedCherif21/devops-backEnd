package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.*;
import com.example.cocomarket.Repository.Cart_Repository;
import com.example.cocomarket.Repository.Commande_Repository;
import com.example.cocomarket.Repository.Produit__Repository;
import com.example.cocomarket.Services.Cart_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@Slf4j
//@AllArgsConstructor
@RequestMapping("/Cart")

public class CartController {

    @Autowired
    Cart_Service cart_service;

    @Autowired
    Cart_Repository car ;

    @Autowired
    Commande_Repository cr ;

    @Autowired
    Produit__Repository produit__repository ;

    @PostMapping("/panier/ajouter-produit/{idProduit}/{idCart}")
    public void ajouterProduit_aupanier(@PathVariable ("idProduit") Integer idProduit,
                                        @PathVariable ("idCart") Integer idCart) {
        cart_service.Add_Product_To_Cart(idProduit,idCart);
    }
    @DeleteMapping("/panier/supprimer-produit/{idProduit}/{idCart}")
    public void supprimerProduit(@PathVariable ("idProduit")Integer idProduit,
                                 @PathVariable ("idCart") Integer idCart) {
        cart_service.Remove_Product(idProduit, idCart);
    }

    @GetMapping("/retrieve-Produit/{Cart-id}/{Produit-id}")
    public Produit retrieveProduit(@PathVariable("Cart-id") Integer idCart,
                                   @PathVariable("Produit-id") Integer idProduit) {
        return cart_service.retrive_one_Product(idCart,idProduit);
    }


    @GetMapping("/retrieve-Product_in_cart/{idCart}")
    public List<Produit> retrieveProduct_in_cart(@PathVariable("idCart") Integer IdCart) {
        return cart_service.retrieveAllProductInCart(IdCart);
    }

    @GetMapping("/cart/{cartId}/products")
    public List<ProduitQuantiteDTO> retrieveAllProductInCart(@PathVariable Integer cartId) {
        CART cart = car.findById(cartId).orElseThrow(null);
        Set<Produit_Cart> items = cart.getItems();

        List<ProduitQuantiteDTO> products = items.stream()
                .map(produitCart -> new ProduitQuantiteDTO(
                        produitCart.getProduit().getId(),
                        produitCart.getProduit().getNom(),
                        produitCart.getProduit().getPrix(),
                        produitCart.getQuantity()))
                .collect(Collectors.toList());

        return products;
    }

    @GetMapping("/numProducts/{cartId}")
    public Integer getNumProducts(@PathVariable("cartId") Integer cartId) {
        // Récupérer le panier correspondant à l'ID donné
        Optional<CART> optionalCart = car.findById(cartId);
        // Récupérer le nombre de produits dans le panier à l'aide de la méthode "getNbProd()" de la classe CART
        return optionalCart.get().getNbProd();
    }

    @GetMapping("/totalprice/{cartId}")
    public Long gettotalprice(@PathVariable("cartId") Integer cartId) {
        // Récupérer le panier correspondant à l'ID donné
        Optional<CART> optionalCart = car.findById(cartId);
        // Récupérer le nombre de produits dans le panier à l'aide de la méthode "getNbProd()" de la classe CART
        return optionalCart.get().getTotal_price();
    }

    @GetMapping("/{cartId}/productQuantities")
    public ResponseEntity<Map<Integer, Integer>> getProductQuantities(@PathVariable Integer cartId) {
        Map<Integer, Integer> productQuantities = Cart_Service.getProductQuantitiesByCartId(cartId);
        return ResponseEntity.ok(productQuantities);
    }

    @GetMapping("/carts/{cartId}/products/names")
    public List<String> getProductsNames(@PathVariable Integer cartId) {
        CART cart = car.findById(cartId).orElseThrow(null);
        List<String> productNames = new ArrayList<>();
        for (Produit_Cart produitCart : cart.getItems()) {
            productNames.add(produitCart.getProduit().getNom());
        }
        return productNames;
    }
}






/**************************PAYPAL**********************************/







