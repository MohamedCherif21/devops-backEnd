package com.example.cocomarket.repository;

import com.example.cocomarket.entity.RaitingDelevryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaitingDelevryManRepository extends JpaRepository<RaitingDelevryMan, Integer> {
}
