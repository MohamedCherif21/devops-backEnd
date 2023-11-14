package com.example.cocomarket.repository;

import com.example.cocomarket.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findByCode(String code);
}
