package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.CART;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Produit_Cart;

import java.util.List;
import java.util.Set;

public interface ICart {

     CART Add_Product_To_Cart(Integer idProduit, Integer idCart);

     void Remove_Product(Integer idProduit,Integer idCart) ;

     Produit retrive_one_Product(Integer idprodCart, Integer idProduit);

     public List<Produit> retrieveAllProductInCart(Integer cartId) ;

     }
