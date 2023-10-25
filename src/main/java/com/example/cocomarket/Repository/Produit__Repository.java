package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Entity.Shop;
import com.example.cocomarket.Entity.Status;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Produit__Repository extends JpaRepository<Produit, Integer> {

    List<Produit> findTop50ByOrderByQuantiteVendueDesc();

  //  Produit findByCategories ( Integer idCategtorie) ;


    //  Produit findByCategories ( Integer idCategtorie) ;


   // Produit findByCategorie ( Integer idCategtorie) ;


    List<Produit> findTop10ByOrderByDatePublicationDesc();

    List<Produit> findByPourcentagePromotionGreaterThan(int i);



}

