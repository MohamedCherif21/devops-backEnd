package com.example.cocomarket.services;

import com.example.cocomarket.entity.Etat_Livraison;
import com.example.cocomarket.entity.Livraison;
import com.example.cocomarket.entity.Vehicule;
import com.example.cocomarket.interfaces.IVehicule;
import com.example.cocomarket.repository.LivraisonRepository;
import com.example.cocomarket.repository.VehiculeRepository;
import com.example.cocomarket.config.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeService implements IVehicule {
EmailSenderService service;
    VehiculeRepository vr;

    LivraisonRepository lr;
    @Override
    public Vehicule addVehicule(Vehicule l) {
        return vr.save(l);
    }

    @Override
    public Vehicule updateVehicule(Vehicule l) {
        return vr.save(l);
    }

    @Override
    public Vehicule findbyidVehicule(Integer idV) {
        Vehicule r;
        r=vr.findById(idV).orElse(null);
        return r;
    }

    @Override
    public void deleteVehicule(Integer idV) {
vr.deleteById(idV);
    }

    @Override
    public List<Vehicule> retrieveAllVehicule() {
        return vr.findAll();
    }

    @Override
    public void affecterLivtocar(String region) {

        List<Livraison> lv=lr.getnotaffectedLivraison(region);
        Vehicule v=vr.getnotaffectedVehicule(region);


          for (Livraison j:lv){
            if(v.getWheight()>j.getVolumeL()&&v.getVolume()>j.getVolumeL()){
             v.getLivcar().add(j);
             j.setEtat(Etat_Livraison.valueOf("Prise_en_compte"));
            }
          }
          v.getUsercar().setAvailability(false);
          vr.save(v);
        service.sendSimpleEmail(v.getUsercar().getEmail(),"you got new mission go check it  :","New mission ");
      }




    @Override
    public Vehicule availablecar(String region) {
        return vr.getnotaffectedVehicule(region);
    }

    @Override
    public void validatemission(Integer id) {
        Vehicule v=vr.getusertovalidate(id);
        v.getUsercar().setAvailability(true);

        List<Livraison> lv= v.getLivcar();
        for(Livraison i:lv){
            i.setDateArrive(LocalDate.now());
            i.setEtat(Etat_Livraison.valueOf("Livrer"));
            i.setValidation(true);
            lr.save(i);
        }
         vr.save(v);
    }


}
