package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByEmail(String email);

    User findById(int id);

    List<User> findAllByOrderByScoreDesc();

}
