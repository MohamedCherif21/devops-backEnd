package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    List<Produit> findTop50ByOrderByQuantiteVendueDesc();

    List<Produit> findTop10ByOrderByDatePublicationDesc();

    List<Produit> findByPourcentagePromotionGreaterThan(int i);


}

