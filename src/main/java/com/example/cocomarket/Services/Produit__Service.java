package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.*;
import com.example.cocomarket.Interfaces.IProduit;
import com.example.cocomarket.Repository.Categorie_Repository;
import com.example.cocomarket.Repository.Produit__Repository;
import com.example.cocomarket.Repository.Rainting_Product_Repository;
import com.example.cocomarket.Repository.Shop_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Produit__Service implements IProduit {
    @Autowired
    private Produit__Repository produitRepository;

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }


       public List<Produit> getProduitByIds(Integer id) {
            return (List<Produit>) produitRepository.findById(id).orElse(null);
    }
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Produit updateProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public void deleteProduitById(Integer id) {
        produitRepository.deleteById(id);
    }

    @Autowired
    Produit__Repository prorepo ;

    @Autowired
    Categorie_Repository cr ;

    @Autowired
    Shop_Repository Shoprepo ;

    @Autowired
    Rainting_Product_Repository raitingProduitrepo ;


    @Override
    public Produit AddnewProduit(Produit Pr) {
        return prorepo.save(Pr);
    }
    @Override
    public void AddProduitAffeASHopAndAffeAcategorie(Integer idProduit, Integer idShop, Integer idCateg) {
        Produit produit = prorepo.findById(idProduit).orElse(null) ;
        Shop shopes = Shoprepo.findById(idShop).orElse(null);
        Categorie categorie = cr.findById(idCateg).orElse(null);
        produit.setCategories(categorie);
        produit.setShopes(shopes);
        prorepo.save(produit);

    }


    @Override
    public void AddRaitingtoProduit(Integer idrainting ,Integer idProduit) {

        Produit produit = prorepo.findById(idProduit).orElse(null) ;
        Raiting_Product R = raitingProduitrepo.findById(idrainting).orElse(null) ;
        produit.getRaiting_products().add(R) ;
        prorepo.save(produit) ;

    }



    @Override
    public String SumRatting(Integer id , Integer id2 ) {

        int a=0;
        int b=0 ;
        Produit p=  prorepo.findById(id).orElse(null);
        Produit p2= prorepo.findById(id2).orElse(null) ;

        for (Raiting_Product j: p2.getRaiting_products()) {
            b += j.getScore();
        }

        for(Raiting_Product i:p.getRaiting_products()){
            a+=i.getScore();
        }
        if ( a>b )
            return ("Produit 1 Better than B " )
                    ;
        else
            return (" Produit 2 Better than Produit 1 ") ;

    }

    public List<Produit> Recomendation(Integer idproduit , Integer idCateg ) {
        List<Produit> p= prorepo.findAll();
        List<Produit> p2 = new ArrayList<>();

        Categorie cate=  cr.findById(idCateg).orElse(null) ;
        for ( Produit i : p )
        { if ( i.getCategories()==cate )
            p2.add(i) ;

        }
        return p2;

    }
}
