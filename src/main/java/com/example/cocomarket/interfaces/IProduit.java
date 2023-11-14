package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Produit;

import java.util.List;

public interface IProduit {

    Produit addnewProduit(Produit pr ) ;

    void addProduitAffeASHopAndAffeAcategorie(Integer idProduit, Integer idShop , Integer idCateg );

    void addRaitingtoProduit(Integer idRaiting, Integer idProduit) ;
    String sumRatting(Integer id , Integer id2);
    List<Produit> getAllProduits();

    public List<Produit> recomendation(Integer idproduit , Integer idCateg ) ;

    public List<Produit> getProduitByIds(Integer id) ;




}
