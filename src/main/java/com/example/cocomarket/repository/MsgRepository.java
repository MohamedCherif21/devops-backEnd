package com.example.cocomarket.repository;

import com.example.cocomarket.entity.MSG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgRepository extends JpaRepository<MSG, Integer> {
}
