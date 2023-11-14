package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Raiting_Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaintingProductRepository extends JpaRepository<Raiting_Product, Integer> {
}
