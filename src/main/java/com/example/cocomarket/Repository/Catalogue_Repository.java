package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Catalogue_Repository extends JpaRepository<Catalogue, Integer> {

    @Query("SELECT MAX(c.id) FROM Catalogue c")
    int findLastCatalogueId();

    @Query("SELECT MIN(c.id) FROM Catalogue c")
    int findFirstCatalogueId();



}
