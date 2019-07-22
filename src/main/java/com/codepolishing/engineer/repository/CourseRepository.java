package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
}
