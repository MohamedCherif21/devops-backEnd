package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.MSG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Msg__Repository extends JpaRepository<MSG, Integer> {
}
