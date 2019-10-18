package com.codepolishing.engineer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course")
    private int id;

    @NotNull
    @Column(name = "id_course_level")
    private int idCourseLevel;

    @NotNull
    @Column(name = "id_course_type")
    private int idCourseType;

    @NotNull
    @Length(max = 100)
    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    @OneToMany
    @JoinColumn(name = "id_course")
    private List<CourseSection> courseSectionList;
    //endregion

    @ManyToMany
    @JoinTable(
            name = "joining_to_courses",
            joinColumns = @JoinColumn(name = "id_course"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_user","id_course"})
    )
    private List<User> userList;


}
