package com.example.cocomarket.Controller;


import com.example.cocomarket.Services.Produit_Cart_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/Produit_cart")
public class Produit_CartController {

    @Autowired
    Produit_Cart_Service produit_Cart_Service ;

    @GetMapping("/{cartId}/products/{produitId}/quantity")
    public ResponseEntity<Integer> getQuantityByCartIdAndProduitId(@PathVariable Integer cartId,
                                                                   @PathVariable Integer produitId) {
        Integer quantity = produit_Cart_Service.getQuantityByCartIdAndProduitId(cartId, produitId);
        return ResponseEntity.ok(quantity);
    }

    @DeleteMapping("/{cartId}/{idprod}/removefromcart")
    public void deleteProduit(@PathVariable Integer cartId,
                              @PathVariable Integer idprod) {
        produit_Cart_Service.deleteProduitById(cartId,idprod);
    }
}
