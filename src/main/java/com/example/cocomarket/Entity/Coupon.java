package com.example.cocomarket.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;
    private String code;
    private double discount;
    private boolean isUsed;

    public Coupon(String code, double discount) {
        this.code = code;
        this.discount = discount;
        this.isUsed = false;
    }

    public String getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

}

