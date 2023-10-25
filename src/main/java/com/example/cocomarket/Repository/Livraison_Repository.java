package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Commande;
import com.example.cocomarket.Entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Livraison_Repository extends JpaRepository<Livraison, Integer> {
    @Query("SELECT l FROM Livraison l where l.region = :region and l.etat='En_attente' ")
    public List<Livraison> getnotaffectedLivraison(@Param("region") String region);



}
