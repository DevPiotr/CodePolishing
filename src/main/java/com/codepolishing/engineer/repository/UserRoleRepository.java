package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    UserRole findByName(String name);
}
