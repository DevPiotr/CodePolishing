package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> findCoursesByIdCourseLevel(int idCourseLevel);
}
