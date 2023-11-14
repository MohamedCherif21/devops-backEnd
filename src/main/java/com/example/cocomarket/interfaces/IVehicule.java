package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Vehicule;

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
