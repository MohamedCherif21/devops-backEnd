package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Vehicule;
import com.example.cocomarket.Repository.Vehicule_Repository;

import java.util.List;

public interface IVehicule {
    public Vehicule addVehicule (Vehicule l);
    public Vehicule updateVehicule (Vehicule l);
    public Vehicule findbyidVehicule (Integer idV);
    void deleteVehicule(Integer idV);
    public List<Vehicule> retrieveAllVehicule();
    public void affecterLivtocar(String region);
    public Vehicule  availablecar(String region);
  public void validatemission(Integer id);

}
