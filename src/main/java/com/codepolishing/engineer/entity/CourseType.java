package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "course_types")
@Data
public class CourseType {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course_type")
    private int id;

    @NotNull
    @Length(max = 45)
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course_type")
    private List<Course> courseList;

    @ManyToMany
    @JoinTable(
            name = "personalized_course_types",
            joinColumns = @JoinColumn(name = "id_course_type"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_type","id_user"})
    )
    private List<User> userList;

    //endregion
}
