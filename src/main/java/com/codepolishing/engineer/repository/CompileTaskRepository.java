package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.CompileTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompileTaskRepository extends JpaRepository<CompileTask,Integer> {

    CompileTask findById(int id);
}
