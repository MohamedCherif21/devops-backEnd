package com.example.cocomarket.services;

import com.example.cocomarket.entity.Raiting_Product;
import com.example.cocomarket.interfaces.IRaintingProduct;
import com.example.cocomarket.repository.RaintingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Rainting_Product_Service implements IRaintingProduct {



    @Autowired
    RaintingProductRepository raitingProduitrepo ;


    @Override
    public Raiting_Product addNEwRaitingProduit(Raiting_Product rp) {
        return raitingProduitrepo.save(rp);
    }
}
