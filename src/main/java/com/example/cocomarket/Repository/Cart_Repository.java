package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.CART;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart_Repository extends JpaRepository<CART, Integer> {
}
