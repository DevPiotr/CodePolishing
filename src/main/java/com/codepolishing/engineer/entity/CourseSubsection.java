package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "course_subsections")
@Data
public class CourseSubsection {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course_subsection")
    private int id;

    @NotNull
    @Column(name = "id_course_section")
    private int idCourseSection;

    @NotNull
    @Length(max = 40)
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "set_of_tasks",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_task"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_subsection","id_task"})
    )
    private List<Task> taskList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course_subsection")
    private List<Opinion> opinionList;

    @ManyToMany
    @JoinTable(
            name = "set_of_theories",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_theory"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_theory","id_course_subsection"})
    )
    private List<Theory> theoryList;

    //endregion
}
