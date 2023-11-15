package com.example.cocomarket.services;

import com.example.cocomarket.entity.*;
import com.example.cocomarket.interfaces.IProduit;
import com.example.cocomarket.repository.CategorieRepository;
import com.example.cocomarket.repository.ProduitRepository;
import com.example.cocomarket.repository.RaintingProductRepository;
import com.example.cocomarket.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProduit {
    @Autowired
    private ProduitRepository produitRepository;

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }


       public List<Produit> getProduitByIds(Integer id) {
            return (List<Produit>) produitRepository.findById(id).orElse(null);
    }


    public Produit retrieveProduct(Integer id) {
        return produitRepository.findById(id).orElseThrow(() -> new NullPointerException("Product not found"));
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
    ProduitRepository prorepo ;

    @Autowired
    CategorieRepository cr ;

    @Autowired
    ShopRepository shoprepo;

    @Autowired
    RaintingProductRepository raitingProduitrepo ;


    @Override
    public Produit addnewProduit(Produit pr) {
        return prorepo.save(pr);
    }
    @Override
    public void addProduitAffeASHopAndAffeAcategorie(Integer idProduit, Integer idShop, Integer idCateg) {
        Produit produit = prorepo.findById(idProduit).orElse(null);
        Shop shopes = shoprepo.findById(idShop).orElse(null);
        Categorie categorie = cr.findById(idCateg).orElse(null);

        if (produit != null && shopes != null && categorie != null) {
            produit.setCategories(categorie);
            produit.setShopes(shopes);
            prorepo.save(produit);
        }
    }



    @Override
    public void addRaitingtoProduit(Integer idrainting, Integer idProduit) {
        Produit produit = prorepo.findById(idProduit).orElse(null);
        Raiting_Product r = raitingProduitrepo.findById(idrainting).orElse(null);

        // Check if both objects are not null before proceeding
        if (produit != null && r != null) {
            produit.getRaitingproducts().add(r);
            prorepo.save(produit);
        } 
    }



    @Override
    public String sumRatting(Integer id, Integer id2) {
        int a = 0;
        int b = 0;
        Produit p = prorepo.findById(id).orElse(null);
        Produit p2 = prorepo.findById(id2).orElse(null);

        // Check if both objects are not null before proceeding
        if (p != null && p2 != null) {
            for (Raiting_Product j : p2.getRaitingproducts()) {
                b += j.getScore();
            }

            for (Raiting_Product i : p.getRaitingproducts()) {
                a += i.getScore();
            }

            if (a > b)
                return ("Produit 1 Better than B ");
            else
                return (" Produit 2 Better than Produit 1 ");
        } else {
            return "Error: One or more produits are null.";
        }
    }


    public List<Produit> recomendation(Integer idproduit , Integer idCateg ) {
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
