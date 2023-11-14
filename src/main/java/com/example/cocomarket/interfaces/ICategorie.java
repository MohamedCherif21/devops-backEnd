package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Categorie;

import java.util.List;

public interface ICategorie {

    Categorie addnewCategorie(Categorie cg ) ;
    public void addSubnewCategorie(Categorie cat) ;

    public List<Categorie> getAllcategories() ;
}
