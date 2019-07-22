package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
