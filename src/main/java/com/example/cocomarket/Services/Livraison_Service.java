package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;
import com.example.cocomarket.Interfaces.ILivraison;
import com.example.cocomarket.Repository.Livraison_Repository;
import com.example.cocomarket.Repository.Raiting_DelevryMan_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Livraison_Service implements ILivraison {
@Autowired
    Livraison_Repository lr;
    @Autowired
    Raiting_DelevryMan_Repository rr;

    @Override
    public Livraison addLivraison(Livraison l) {
return lr.save(l);   }

    @Override
    public Livraison updateLivraison(Livraison l) {
     return lr.save(l);
    }

    @Override
    public Livraison findbyidLivraison(Integer idliv) {
    return lr.findById(idliv).orElse(null);
    }

    @Override
    public void deleteLiv(Integer idLiv) {
     lr.deleteById(idLiv);
    }

    @Override
    public List<Livraison> retrieveAllLiv() {
        return lr.findAll();
    }

    @Override
    public Raiting_DelevryMan affecterrattingtolivaison(Integer id_l, Raiting_DelevryMan r) {
      Livraison lv =  lr.findById(id_l).orElse(null);
        lv.setRating_Liv(r);
        return rr.save(r);

    }

    @Override
    public List<Livraison> notaffected(String region) {
        return lr.getnotaffectedLivraison(region);
    }

}
