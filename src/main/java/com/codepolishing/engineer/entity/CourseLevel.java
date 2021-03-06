package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "course_levels")
@Data
public class CourseLevel {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course_level")
    private int id;

    @NotNull
    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course_level")
    private List<Course> courseList;

    //endregion
}
