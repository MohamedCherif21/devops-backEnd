package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageData ,Integer> {
    ImageData findByProduitId(Integer produitId);
}
