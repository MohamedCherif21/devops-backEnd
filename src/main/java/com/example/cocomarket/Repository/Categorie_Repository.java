package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Categorie_Repository extends JpaRepository<Categorie, Integer> {
}
