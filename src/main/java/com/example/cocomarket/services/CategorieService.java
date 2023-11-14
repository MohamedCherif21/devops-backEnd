package com.example.cocomarket.services;

import com.example.cocomarket.entity.Categorie;
import com.example.cocomarket.interfaces.ICategorie;
import com.example.cocomarket.repository.CategorieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategorieService implements ICategorie {

    @Autowired
    CategorieRepository cr ;


    @Override
    public Categorie addnewCategorie(Categorie cg) {
        return cr.save(cg);
    }

    @Override
    public void addSubnewCategorie(Categorie cat) {
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