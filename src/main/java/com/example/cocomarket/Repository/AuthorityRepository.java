package com.example.cocomarket.Repository;

import com.example.cocomarket.Entity.Autority;
import com.example.cocomarket.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<Autority, Integer> {

    @Query("SELECT c FROM Autority c where c.name = :role ")
    public List<Autority> findAllUserByRole(@Param("role") String role);


}
