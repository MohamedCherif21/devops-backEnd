package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Integer>  {
    @Query("SELECT v FROM Vehicule v where v.usercar.region = :region and v.usercar.availability=true  ")
    public Vehicule getnotaffectedVehicule(@Param("region") String region);

    @Query("SELECT v FROM Vehicule v where v.usercar.id = :id and v.usercar.availability=false  ")
    public Vehicule getusertovalidate(@Param("id") Integer id);


}
