package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProductService produitService;

    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitService.createProduit(produit);
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Integer id) {
        return produitService.getProduitByIds(id).get(id);
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
