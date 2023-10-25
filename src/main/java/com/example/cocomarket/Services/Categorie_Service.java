package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Categorie;
import com.example.cocomarket.Interfaces.ICategorie;
import com.example.cocomarket.Repository.Categorie_Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Categorie_Service implements ICategorie {

    @Autowired
    Categorie_Repository cr ;


    @Override
    public Categorie AddnewCategorie(Categorie cg) {
        return cr.save(cg);
    }

    @Override
    public void AddSubnewCategorie(Categorie cat) {
        addsubCatergorie(cat) ;
        cr.save(cat) ;
    }

    @Override
    public List<Categorie> getAllcategories() {
        return cr.findAll();
    }



    private void addsubCatergorie ( Categorie categoryy)
    {
        for( Categorie subCateroy : categoryy.subCatergories() ) {
            addsubCatergorie(subCateroy);
            cr.save(subCateroy);
        }
    }
}