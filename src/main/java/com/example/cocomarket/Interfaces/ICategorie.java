package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Categorie;

import java.util.List;

public interface ICategorie {

    Categorie AddnewCategorie (Categorie cg ) ;
    public void AddSubnewCategorie(Categorie cat) ;

    public List<Categorie> getAllcategories() ;
}
