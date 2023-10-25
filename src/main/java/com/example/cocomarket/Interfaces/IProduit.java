package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Shop;
import com.example.cocomarket.Entity.Status;
import com.example.cocomarket.Services.Produit__Service;

import java.util.List;
import java.util.Optional;

public interface IProduit {

    Produit AddnewProduit (Produit Pr ) ;

    void AddProduitAffeASHopAndAffeAcategorie ( Integer idProduit, Integer idShop , Integer idCateg );

    void AddRaitingtoProduit(Integer idRaiting, Integer idProduit) ;
    String  SumRatting(Integer id , Integer id2);
    List<Produit> getAllProduits();

    public List<Produit> Recomendation(Integer idproduit , Integer idCateg ) ;

    public List<Produit> getProduitByIds(Integer id) ;




}
