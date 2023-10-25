package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Shop;
import com.example.cocomarket.Entity.Status;
import com.example.cocomarket.Services.Produit__Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private Produit__Service produitService;

    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitService.createProduit(produit);
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Integer id) {
        Produit produit = produitService.getProduitByIds(id).get(id);
        return produit;
    }

    @GetMapping
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }

    @PutMapping
    public Produit updateProduit(@RequestBody Produit produit) {
        return produitService.updateProduit(produit);
    }

    @DeleteMapping("/{id}")
    public void deleteProduitById(@PathVariable Integer id) {
        produitService.deleteProduitById(id);
    }



}
