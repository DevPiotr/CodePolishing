package com.codepolishing.engineer.entity;

import lombok.Data;
import lombok.ToString;
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

    @NotNull
    @Column(name = "subsection_part")
    private int subsectionPart;

    @ManyToMany
    @JoinTable(
            name = "set_of_tasks",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_task"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_subsection","id_task"})
    )
    private List<Task> taskList;

    @ManyToMany
    @JoinTable(
            name = "set_of_theories",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_theory"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_theory","id_course_subsection"})
    )
    private List<Theory> theoryList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course_subsection")
    private List<Opinion> opinionList;

    @ManyToMany
    @JoinTable(
            name = "set_of_compile_tasks",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_compile_task"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_compile_task","id_course_subsection"})
    )
    private List<Theory> compileTaskList;


    //endregion
}
