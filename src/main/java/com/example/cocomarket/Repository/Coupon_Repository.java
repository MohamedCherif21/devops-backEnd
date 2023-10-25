package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface Coupon_Repository extends JpaRepository<Coupon, Integer> {

    Coupon findByCode(String code);
}
