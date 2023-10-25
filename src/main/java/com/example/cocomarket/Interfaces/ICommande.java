package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Commande;
import com.example.cocomarket.Entity.Coupon;
import com.example.cocomarket.Entity.Livraison;

import java.util.List;
import java.util.Set;


public interface ICommande {
    public Livraison affectercamandtolivaison(String Region, Livraison l) ;

    public Commande Confirmer_Commande(Commande commande, Integer idCart,String couponCode);

    List<Commande> rechercher(String parametre);

    void Annuler_Commande(Integer idCommande) ;

    Set<Commande> Afficher_AllCommandes(Integer idcart);

    Commande Afficher_Commandes(Integer idCommande, Integer idcart);

    public List<Commande> Afficher_AllCommandesadmin();

    void Accepter_commande(Integer idcommande);

    void Refuser_commande(Integer idcommande);


    //public Livraison affectercamandtolivaison(Integer id_c,Livraison l);
}
