package com.example.cocomarket.controller;


import com.example.cocomarket.services.Produit_Cart_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/Produit_cart")
public class ProduitCartController {

    @Autowired
    Produit_Cart_Service produitCartService;

    @GetMapping("/{cartId}/products/{produitId}/quantity")
    public ResponseEntity<Integer> getQuantityByCartIdAndProduitId(@PathVariable Integer cartId,
                                                                   @PathVariable Integer produitId) {
        Integer quantity = produitCartService.getQuantityByCartIdAndProduitId(cartId, produitId);
        return ResponseEntity.ok(quantity);
    }

    @DeleteMapping("/{cartId}/{idprod}/removefromcart")
    public void deleteProduit(@PathVariable Integer cartId,
                              @PathVariable Integer idprod) {
        produitCartService.deleteProduitById(cartId,idprod);
    }
}
