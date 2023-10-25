package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Shop_Repository extends JpaRepository<Shop, Integer> {
}
