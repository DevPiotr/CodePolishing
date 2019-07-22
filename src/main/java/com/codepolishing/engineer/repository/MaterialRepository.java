package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material,Integer> {
}
