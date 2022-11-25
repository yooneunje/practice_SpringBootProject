package com.example.mythymleaf.repository;

import com.example.mythymleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

}
