package com.example.cocomarket.services;

import com.example.cocomarket.entity.Livraison;
import com.example.cocomarket.entity.RaitingDelevryMan;
import com.example.cocomarket.interfaces.ILivraison;
import com.example.cocomarket.repository.LivraisonRepository;
import com.example.cocomarket.repository.RaitingDelevryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivraisonService implements ILivraison {
@Autowired
LivraisonRepository lr;
    @Autowired
    RaitingDelevryManRepository rr;

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
    public RaitingDelevryMan affecterrattingtolivaison(Integer idL, RaitingDelevryMan r) {
      Livraison lv =  lr.findById(idL).orElse(null);
        lv.setRatingLiv(r);
        return rr.save(r);

    }

    @Override
    public List<Livraison> notaffected(String region) {
        return lr.getnotaffectedLivraison(region);
    }

}
