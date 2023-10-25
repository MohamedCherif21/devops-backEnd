package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Koffa;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Repository.Koffa_Repository;
import com.example.cocomarket.Services.Koffa_Service;
import com.example.cocomarket.Services.Produit__Service;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/koffas")
public class KoffaController {
    @Autowired
    private Koffa_Service koffaService;
    @Autowired
    private Koffa_Repository koffa_Repository;

    @Autowired
    private Produit__Service produit__service;

    @PostMapping("/koffas")
    public Koffa createKoffa(@RequestBody Koffa koffa) {
        return koffa_Repository.save(koffa);
    }

    @GetMapping
    public ResponseEntity<List<Koffa>> getAllKoffas() {
        List<Koffa> koffas = koffaService.getAllKoffas();
        return new ResponseEntity<>(koffas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koffa> getKoffaById(@PathVariable("id") Integer id) {
        Koffa koffa = koffaService.getKoffaById(id);
        if (koffa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(koffa, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Koffa> saveKoffa(@RequestBody Koffa koffa) {
        Koffa savedKoffa = koffaService.saveKoffa(koffa);
        return new ResponseEntity<>(savedKoffa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koffa> updateKoffa(@PathVariable("id") Integer id, @RequestBody Koffa koffa) {
        Optional<Koffa> koffaData = Optional.ofNullable(koffaService.getKoffaById(id));

        if (koffaData.isPresent()) {
            Koffa _koffa = koffaData.get();
            _koffa.setNom_association(koffa.getNom_association());
            _koffa.setDescription(koffa.getDescription());
            _koffa.setUserKoffa(koffa.getUserKoffa());
            _koffa.setProduits_Koffa(koffa.getProduits_Koffa());
            Koffa updatedKoffa = koffaService.saveKoffa(_koffa);
            return new ResponseEntity<>(updatedKoffa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{koffaId}/add-products")
    public ResponseEntity<Koffa> addProductsToKoffa(@PathVariable("koffaId") Integer koffaId, @RequestBody Integer productIds) {
        Koffa koffa = koffaService.getKoffaById(koffaId);
        if (koffa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Produit> produits = produit__service.getProduitByIds(productIds);
        koffa.getProduits_Koffa().addAll(produits);

        // Save the updated Koffa to the database
        Koffa updatedKoffa = koffaService.saveKoffa(koffa);

        return new ResponseEntity<>(updatedKoffa, HttpStatus.OK);
    }



}
