package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Contrat_Repository extends JpaRepository<Contrat, Integer> {
}
