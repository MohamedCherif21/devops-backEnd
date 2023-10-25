package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Publication__Repository extends JpaRepository<Publication, Integer> {
}
