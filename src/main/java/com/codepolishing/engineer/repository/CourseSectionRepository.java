package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSectionRepository extends JpaRepository<CourseSection,Integer> {
    List<CourseSection> findCourseSectionsByIdCourse(int idCourse);

}
