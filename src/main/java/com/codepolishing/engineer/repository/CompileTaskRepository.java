package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.CompileTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompileTaskRepository extends JpaRepository<CompileTask,Integer> {
}
