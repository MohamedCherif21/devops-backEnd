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
public class Shop_Service implements IShop {
    @Autowired
    ShopRepository Shoprepo ;

    @Autowired
    ContratRepository contratrepo ;


    @Override
    public List<Shop> afficherLesShop() {
        return
                Shoprepo.findAll();
    }

    @Override
    public void supprimerShop(int id) {
        Shoprepo.deleteById(id);
    }

    @Override
    public Shop RetriveByid(int id) {
        return Shoprepo.findById(id).orElse(null);
    }

    @Override
    public Shop addNewshop(Shop shp ) {
        return Shoprepo.save(shp);
    }

    @Override
    public void addContratToShop(Integer idcontrat , Integer idshop) {


        Shop shop = Shoprepo.findById(idshop).orElse(null) ;
        Contrat c = contratrepo.findById(idcontrat).orElse(null) ;
        shop.setContratShop(c);  ;
        Shoprepo.save(shop) ;

    }

}
