package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Comentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Comentaire_Repository extends JpaRepository<Comentaire, Integer> {
}
