package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Commande;
import com.example.cocomarket.Entity.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface Commande_Repository extends JpaRepository<Commande, Integer> {

    @Query("SELECT c FROM Commande c where c.buyer_address = :region and c.etat=:WAITING ")
    public List<Commande> getnotaffectedCommand(@Param("region") String region);

    @Query("SELECT c FROM Commande c where c.etat =: WAITING ")
    public Commande traiterCommand();

    @Query(value = "SELECT * FROM commande " +
            "WHERE shop_name LIKE %:parametre% " +
            "OR buyer_email LIKE %:parametre% " +
            "OR description LIKE %:parametre%", nativeQuery = true)
    List<Commande> rechercher(@Param("parametre") String parametre);

    @Query(value ="SELECT commande_cart_id FROM commande WHERE etat like 'VALIDATED' GROUP BY commande_cart_id HAVING COUNT(commande_cart_id) > 3 ORDER BY COUNT(commande_cart_id) DESC", nativeQuery = true)
    Integer thewinneroftheyear();


    public List<Commande> findByEtat(Etat etat);

}
