package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Contrat;
import com.example.cocomarket.Entity.Shop;
import com.example.cocomarket.Interfaces.IShop;
import com.example.cocomarket.Repository.Contrat_Repository;
import com.example.cocomarket.Repository.Shop_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Shop_Service implements IShop {
    @Autowired
    Shop_Repository Shoprepo ;

    @Autowired
    Contrat_Repository contratrepo ;


    @Override
    public List<Shop> AfficherLesShop() {
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
    public Shop AddNewshop(Shop shp ) {
        return Shoprepo.save(shp);
    }

    @Override
    public void AddContratToShop(Integer idcontrat ,Integer idshop) {


        Shop shop = Shoprepo.findById(idshop).orElse(null) ;
        Contrat c = contratrepo.findById(idcontrat).orElse(null) ;
        shop.setContrat_shop(c);  ;
        Shoprepo.save(shop) ;

    }

}
