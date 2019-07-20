package com.codepolishing.engineer.dao;

import com.codepolishing.engineer.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
}
