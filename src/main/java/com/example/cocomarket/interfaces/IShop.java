package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Shop;

import java.util.List;

public interface IShop {
    List<Shop> afficherLesShop() ;
    void supprimerShop(int id);

    Shop RetriveByid(int id);

    Shop addNewshop(Shop shp  ) ;

    public void addContratToShop(Integer idcontrat , Integer idshop) ;

}
