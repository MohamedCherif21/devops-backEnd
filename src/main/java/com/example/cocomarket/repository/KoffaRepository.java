package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Koffa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoffaRepository extends JpaRepository<Koffa, Integer> {
}
