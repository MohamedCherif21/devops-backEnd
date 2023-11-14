package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Integer> {

    @Query("SELECT MAX(c.id) FROM Catalogue c")
    int findLastCatalogueId();

    @Query("SELECT MIN(c.id) FROM Catalogue c")
    int findFirstCatalogueId();



}
