package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Vehicule;
import com.example.cocomarket.Interfaces.IVehicule;
import com.example.cocomarket.Repository.Vehicule_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Vehicule")
public class Vehicule_Controller {
    @Autowired
    IVehicule iv;
    @Autowired
    Vehicule_Repository vr;
    @PostMapping("/add-Car")
    public Vehicule addCar(@RequestBody Vehicule l) {
        Vehicule li = iv.addVehicule(l);
        return li;
    }
    @PutMapping("/update-Car")
    public Vehicule updateCar(@RequestBody Vehicule l) {
        Vehicule li = iv.updateVehicule(l);
        return li;
    }
    @DeleteMapping("/remove-Car/{Car-id}")
    public void removeLiv(@PathVariable("Car-id") Integer idc) {
    iv.deleteVehicule(idc);
    }
    @GetMapping("/retrieve-all-Cars")
    @PreAuthorize("hasAuthority('DELEVRY')")
    public List<Vehicule> getlivs() {
        List<Vehicule> listLiv = iv.retrieveAllVehicule();
        return listLiv;
    }
    @GetMapping("/retrieve-Liv/{Car-id}")
    public Vehicule getliv(@PathVariable("Car-id") Integer idc) {
        Vehicule Liv = iv.findbyidVehicule(idc);
        return Liv;
    }

    @PutMapping("/assign-liv-car/{region}")
    public void Livwithcar(@PathVariable("region") String region)
    {
       iv.affecterLivtocar(region);
    }


    @GetMapping("/retrieve-Liv-available/{region}")
    public Vehicule getliv(@PathVariable("region") String region) {
       Vehicule Liv = iv.availablecar(region);
        return Liv;
    }

    @PutMapping("/validate-liv-car/{id}")
    public void Validate(@PathVariable("id") Integer id)
    {
        iv.validatemission(id);
    }
    @GetMapping("/getliv-byid/{id}")
    public List<Livraison>getLiv(@PathVariable("id") Integer id){
        List<Livraison> l ;
        Vehicule v=vr.getusertovalidate(id);

        List<Livraison> lv= v.getLiv_car();
        return  lv;
    }

}
