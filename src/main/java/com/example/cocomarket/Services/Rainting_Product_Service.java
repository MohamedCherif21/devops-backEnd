package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Raiting_Product;
import com.example.cocomarket.Interfaces.IRainting_Product;
import com.example.cocomarket.Repository.Rainting_Product_Repository;
import com.example.cocomarket.filter.Myfilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Rainting_Product_Service implements IRainting_Product {



    @Autowired
    Rainting_Product_Repository raitingProduitrepo ;


    @Override
    public Raiting_Product AddNEwRaitingProduit(Raiting_Product Rp) {
        String output = Myfilter.getCensoredText(Rp.getFeedBack());
        System.out.println(output);
        Rp.setFeedBack(output);
        return raitingProduitrepo.save(Rp);
    }
}
