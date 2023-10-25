package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Vehicule_Repository extends JpaRepository<Vehicule, Integer>  {
    @Query("SELECT v FROM Vehicule v where v.user_car.region = :region and v.user_car.availability=true  ")
    public Vehicule getnotaffectedVehicule(@Param("region") String region);

    @Query("SELECT v FROM Vehicule v where v.user_car.id = :id and v.user_car.availability=false  ")
    public Vehicule getusertovalidate(@Param("id") Integer id);


}
