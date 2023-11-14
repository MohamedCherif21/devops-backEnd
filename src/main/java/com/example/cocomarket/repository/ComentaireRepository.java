package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Comentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentaireRepository extends JpaRepository<Comentaire, Integer> {
}
