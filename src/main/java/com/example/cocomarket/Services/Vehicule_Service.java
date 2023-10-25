package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Etat_Livraison;
import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Vehicule;
import com.example.cocomarket.Interfaces.IVehicule;
import com.example.cocomarket.Repository.Livraison_Repository;
import com.example.cocomarket.Repository.Vehicule_Repository;
import com.example.cocomarket.config.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class Vehicule_Service implements IVehicule {
EmailSenderService service;
    Vehicule_Repository vr;

    Livraison_Repository lr;
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
             v.getLiv_car().add(j);
             j.setEtat(Etat_Livraison.valueOf("Prise_en_compte"));

            }else {
                System.out.println("try again after few moment");
            }
          }
          v.getUser_car().setAvailability(false);
          vr.save(v);
        service.sendSimpleEmail(v.getUser_car().getEmail(),"you got new mission go check it  :","New mission ");
      }




    @Override
    public Vehicule availablecar(String region) {
        return vr.getnotaffectedVehicule(region);
    }

    @Override
    public void validatemission(Integer id) {
        Vehicule v=vr.getusertovalidate(id);
        v.getUser_car().setAvailability(true);

        List<Livraison> lv= v.getLiv_car();
        for(Livraison i:lv){
            i.setDate_Arrive(LocalDate.now());
            i.setEtat(Etat_Livraison.valueOf("Livrer"));
            i.setValidation(true);
            lr.save(i);
        }
         vr.save(v);
    }


}
