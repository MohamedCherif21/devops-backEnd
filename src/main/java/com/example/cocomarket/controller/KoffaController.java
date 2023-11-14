package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Koffa;
import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.repository.KoffaRepository;
import com.example.cocomarket.services.KoffaService;
import com.example.cocomarket.services.Produit__Service;
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
    private KoffaService koffaService;
    @Autowired
    private KoffaRepository koffa_Repository;

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
            _koffa.setNomassociation(koffa.getNomassociation());
            _koffa.setDescription(koffa.getDescription());
            _koffa.setUserKoffa(koffa.getUserKoffa());
            _koffa.setProduitsKoffa(koffa.getProduitsKoffa());
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
        koffa.getProduitsKoffa().addAll(produits);

        // Save the updated Koffa to the database
        Koffa updatedKoffa = koffaService.saveKoffa(koffa);

        return new ResponseEntity<>(updatedKoffa, HttpStatus.OK);
    }



}
