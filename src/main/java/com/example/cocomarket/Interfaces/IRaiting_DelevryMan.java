package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;

import java.util.List;

public interface IRaiting_DelevryMan {
    public Raiting_DelevryMan addRaitingL (Raiting_DelevryMan l);
    public Raiting_DelevryMan updateRaitingL (Raiting_DelevryMan l);
    public Raiting_DelevryMan findbyidRaitingL (Integer idliv);
    void deleteRaitingL(Integer idLiv);
    public List<Raiting_DelevryMan> retrieveAllRaitingL();
}
