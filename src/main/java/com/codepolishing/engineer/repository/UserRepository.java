package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByEmail(String email);

    /*@Query(value = "SELECT u.name, u.surname FROM users u ORDER BY u.score ASC")
    List<User> findAllUsersByScore();*/

    List<User> findAllByOrderByScoreDesc();

}
