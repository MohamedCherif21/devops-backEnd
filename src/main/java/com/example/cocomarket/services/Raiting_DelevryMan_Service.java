package com.example.cocomarket.services;

import com.example.cocomarket.entity.RaitingDelevryMan;
import com.example.cocomarket.interfaces.IRaitingDelevryMan;
import com.example.cocomarket.repository.RaitingDelevryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Raiting_DelevryMan_Service implements IRaitingDelevryMan {
    @Autowired
    RaitingDelevryManRepository rr;
    @Override
    public RaitingDelevryMan addRaitingL(RaitingDelevryMan l) {
        return rr.save(l);
    }

    @Override
    public RaitingDelevryMan updateRaitingL(RaitingDelevryMan l) {
        return rr.save(l);
    }

    @Override
    public RaitingDelevryMan findbyidRaitingL(Integer idliv) {
        return  rr.findById(idliv).orElse(null);
    }

    @Override
    public void deleteRaitingL(Integer idLiv) {
      rr.deleteById(idLiv);
    }

    @Override
    public List<RaitingDelevryMan> retrieveAllRaitingL() {
       return rr.findAll();
    }
}
