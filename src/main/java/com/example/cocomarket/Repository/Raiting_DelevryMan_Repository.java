package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Raiting_DelevryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Raiting_DelevryMan_Repository extends JpaRepository<Raiting_DelevryMan, Integer> {
}
