package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;
import com.example.cocomarket.Interfaces.IRaiting_DelevryMan;
import com.example.cocomarket.Repository.Raiting_DelevryMan_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Raiting_DelevryMan_Service implements IRaiting_DelevryMan {
    @Autowired
    Raiting_DelevryMan_Repository rr;
    @Override
    public Raiting_DelevryMan addRaitingL(Raiting_DelevryMan l) {
        return rr.save(l);
    }

    @Override
    public Raiting_DelevryMan updateRaitingL(Raiting_DelevryMan l) {
        return rr.save(l);
    }

    @Override
    public Raiting_DelevryMan findbyidRaitingL(Integer idliv) {
        return  rr.findById(idliv).orElse(null);
    }

    @Override
    public void deleteRaitingL(Integer idLiv) {
      rr.deleteById(idLiv);
    }

    @Override
    public List<Raiting_DelevryMan> retrieveAllRaitingL() {
       return rr.findAll();
    }
}
