package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.CART;
import com.example.cocomarket.entity.Produit;

import java.util.List;

public interface ICart {

     CART addProductToCart(Integer idProduit, Integer idCart);

     void removeProduct(Integer idProduit, Integer idCart) ;

     Produit retriveOneProduct(Integer idprodCart, Integer idProduit);

     public List<Produit> retrieveAllProductInCart(Integer cartId) ;

     }
