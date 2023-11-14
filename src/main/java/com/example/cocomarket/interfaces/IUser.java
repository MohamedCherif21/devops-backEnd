package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Vehicule;

public interface IUser {
    public void assignusertoCar (Integer lid, Integer uId);
    public Vehicule assignusertoCarCreate (Integer uId, Vehicule v);

    void accepterCommande(Integer idcommande);
    void refuserCommande(Integer idcommande);
}
