package com.example.cocomarket.services;

import com.example.cocomarket.entity.Coupon;
import com.example.cocomarket.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository ;


    public Coupon generateCoupon() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }
        Coupon coupon = new Coupon();
        coupon.setCode(code.toString());
        return couponRepository.save(coupon);
    }

    public boolean isCouponValid(String couponCode) {
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByCode(couponCode));
        return optionalCoupon.isPresent();
    }

}
