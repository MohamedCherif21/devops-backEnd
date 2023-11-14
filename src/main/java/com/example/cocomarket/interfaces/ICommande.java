package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Commande;
import com.example.cocomarket.entity.Livraison;

import java.util.List;
import java.util.Set;


public interface ICommande {
    public Livraison affectercamandtolivaison(String region, Livraison l) ;

    public Commande confirmerCommande(Commande commande, Integer idCart, String couponCode);

    List<Commande> rechercher(String parametre);

    void annulerCommande(Integer idCommande) ;

    Set<Commande> afficherAllCommandes(Integer idcart);

    Commande afficherCommandes(Integer idCommande, Integer idcart);

    public List<Commande> afficherAllCommandesadmin();

    void accepterCommande(Integer idcommande);

    void refuserCommande(Integer idcommande);
}
