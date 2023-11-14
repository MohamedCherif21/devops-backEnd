package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Livraison;
import com.example.cocomarket.entity.Vehicule;
import com.example.cocomarket.interfaces.IVehicule;
import com.example.cocomarket.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Vehicule")
public class VehiculeController {
    @Autowired
    IVehicule iv;
    @Autowired
    VehiculeRepository vr;
    @PostMapping("/add-Car")
    public Vehicule addCar(@RequestBody Vehicule l) {
        return iv.addVehicule(l);
    }
    @PutMapping("/update-Car")
    public Vehicule updateCar(@RequestBody Vehicule l) {
        return iv.updateVehicule(l);
    }
    @DeleteMapping("/remove-Car/{Car-id}")
    public void removeLiv(@PathVariable("Car-id") Integer idc) {
    iv.deleteVehicule(idc);
    }
    @GetMapping("/retrieve-all-Cars")
    @PreAuthorize("hasAuthority('DELEVRY')")
    public List<Vehicule> getlivs() {
        return iv.retrieveAllVehicule();
    }
    @GetMapping("/retrieve-Liv/{Car-id}")
    public Vehicule getliv(@PathVariable("Car-id") Integer idc) {
        return iv.findbyidVehicule(idc);
    }

    @PutMapping("/assign-liv-car/{region}")
    public void livwithcar(@PathVariable("region") String region)
    {
       iv.affecterLivtocar(region);
    }


    @GetMapping("/retrieve-Liv-available/{region}")
    public Vehicule getliv(@PathVariable("region") String region) {
        return iv.availablecar(region);
    }

    @PutMapping("/validate-liv-car/{id}")
    public void validate(@PathVariable("id") Integer id)
    {
        iv.validatemission(id);
    }
    @GetMapping("/getliv-byid/{id}")
    public List<Livraison>getLivraison(@PathVariable("id") Integer id){
        Vehicule v=vr.getusertovalidate(id);

        return v.getLivcar();
    }

}
