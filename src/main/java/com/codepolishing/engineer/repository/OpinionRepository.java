package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<Opinion,Integer> {
}
