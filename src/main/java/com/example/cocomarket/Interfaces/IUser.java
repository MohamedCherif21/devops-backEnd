package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Vehicule;

public interface IUser {
    public void assignusertoCar (Integer LId, Integer UId);
    public Vehicule assignusertoCarCreate (Integer UId, Vehicule v);

    void Accepter_commande(Integer idcommande);
    void Refuser_commande(Integer idcommande);
}
