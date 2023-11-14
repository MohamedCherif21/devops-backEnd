package com.example.cocomarket.controller;

import com.example.cocomarket.entity.*;
import com.example.cocomarket.repository.CartRepository;
import com.example.cocomarket.repository.Commande_Repository;
import com.example.cocomarket.repository.ProduitRepository;
import com.example.cocomarket.services.CartService;
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
    CartService cartService;

    @Autowired
    CartRepository car ;

    @Autowired
    Commande_Repository cr ;

    @Autowired
    ProduitRepository produitRepository;

    @PostMapping("/panier/ajouter-produit/{idProduit}/{idCart}")
    public void ajouterProduitAupanier(@PathVariable ("idProduit") Integer idProduit,
                                       @PathVariable ("idCart") Integer idCart) {
        cartService.addProductToCart(idProduit,idCart);
    }
    @DeleteMapping("/panier/supprimer-produit/{idProduit}/{idCart}")
    public void supprimerProduit(@PathVariable ("idProduit")Integer idProduit,
                                 @PathVariable ("idCart") Integer idCart) {
        cartService.removeProduct(idProduit, idCart);
    }

    @GetMapping("/retrieve-Produit/{Cart-id}/{Produit-id}")
    public Produit retrieveProduit(@PathVariable("Cart-id") Integer idCart,
                                   @PathVariable("Produit-id") Integer idProduit) {
        return cartService.retriveOneProduct(idCart,idProduit);
    }


    @GetMapping("/retrieve-Product_in_cart/{idCart}")
    public List<Produit> retrieveProductInCart(@PathVariable("idCart") Integer idCart) {
        return cartService.retrieveAllProductInCart(idCart);
    }

    @GetMapping("/cart/{cartId}/products")
    public List<ProduitQuantiteDTO> retrieveAllProductInCart(@PathVariable Integer cartId) {
        CART cart = car.findById(cartId).orElseThrow(null);
        Set<ProduitCart> items = cart.getItems();

        return items.stream()
                .map(produitCart -> new ProduitQuantiteDTO(
                        produitCart.getProduit().getId(),
                        produitCart.getProduit().getNom(),
                        produitCart.getProduit().getPrix(),
                        produitCart.getQuantity()))
                .collect(Collectors.toList());
    }

    @GetMapping("/numProducts/{cartId}")
    public Integer getNumProducts(@PathVariable("cartId") Integer cartId) {
        // Récupérer le panier correspondant à l'ID donné
        Optional<CART> optionalCart = car.findById(cartId);

        // Check if the Optional is present before accessing the value
        if (optionalCart.isPresent()) {
            // Récupérer le nombre de produits dans le panier à l'aide de la méthode "getNbProd()" de la classe CART
            return optionalCart.get().getNbProd();
        } else {
            // Handle the case when the Optional is empty
            throw new NullPointerException("Cart not found for ID: " + cartId);
        }
    }


    @GetMapping("/totalprice/{cartId}")
    public Long gettotalprice(@PathVariable("cartId") Integer cartId) {
        // Récupérer le panier correspondant à l'ID donné
        Optional<CART> optionalCart = car.findById(cartId);

        // Vérifier si le Optional est présent avant d'accéder à la valeur
        if (optionalCart.isPresent()) {
            // Récupérer le nombre de produits dans le panier à l'aide de la méthode "getNbProd()" de la classe CART
            return optionalCart.get().getTotal_price();
        } else {
            // Gérer le cas où le panier n'est pas présent (par exemple, en lançant une exception ou en renvoyant une valeur par défaut)
            throw new NullPointerException("Cart not found for ID: " + cartId);
            // Ou bien, retourner une valeur par défaut, selon les besoins de votre application
            // return 0L; // ou toute autre valeur par défaut appropriée
        }
    }

    @GetMapping("/{cartId}/productQuantities")
    public ResponseEntity<Map<Integer, Integer>> getProductQuantities(@PathVariable Integer cartId) {
        Map<Integer, Integer> productQuantities = CartService.getProductQuantitiesByCartId(cartId);
        return ResponseEntity.ok(productQuantities);
    }

    @GetMapping("/carts/{cartId}/products/names")
    public List<String> getProductsNames(@PathVariable Integer cartId) {
        CART cart = car.findById(cartId).orElseThrow(null);
        List<String> productNames = new ArrayList<>();
        for (ProduitCart produitCart : cart.getItems()) {
            productNames.add(produitCart.getProduit().getNom());
        }
        return productNames;
    }
}






/**************************PAYPAL**********************************/







