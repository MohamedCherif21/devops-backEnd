package com.example.cocomarket.services;

import com.example.cocomarket.entity.Contrat;
import com.example.cocomarket.entity.Shop;
import com.example.cocomarket.interfaces.IShop;
import com.example.cocomarket.repository.ContratRepository;
import com.example.cocomarket.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService implements IShop {
    @Autowired
    ShopRepository shoprepo;

    @Autowired
    ContratRepository contratrepo ;


    @Override
    public List<Shop> afficherLesShop() {
        return
                shoprepo.findAll();
    }

    @Override
    public void supprimerShop(int id) {
        shoprepo.deleteById(id);
    }

    @Override
    public Shop RetriveByid(int id) {
        return shoprepo.findById(id).orElse(null);
    }

    @Override
    public Shop addNewshop(Shop shp ) {
        return shoprepo.save(shp);
    }

    @Override
    public void addContratToShop(Integer idcontrat, Integer idshop) {
        Shop shop = shoprepo.findById(idshop).orElse(null);
        Contrat c = contratrepo.findById(idcontrat).orElse(null);
        if (shop != null && c != null) {
            shop.setContratShop(c);
            shoprepo.save(shop);
        }
    }


}
