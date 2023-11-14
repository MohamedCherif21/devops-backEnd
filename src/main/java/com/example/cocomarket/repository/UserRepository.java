package com.example.cocomarket.repository;

import com.example.cocomarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.region = :region and u.availability=false")
    public List<User> getnotavailableuser(@Param("region") String region);

    Optional<User> findByEmail(String email);

    @Query(value = " SELECT * FROM _user WHERE email = :mail  ",
            nativeQuery = true)
    User FoundAcountBYMail(@Param("mail") String mail);



}
