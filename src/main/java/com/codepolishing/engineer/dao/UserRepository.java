package com.codepolishing.engineer.dao;

import com.codepolishing.engineer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
