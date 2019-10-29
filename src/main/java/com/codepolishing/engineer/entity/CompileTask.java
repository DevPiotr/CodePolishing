package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "compile_tasks")
@Data
public class CompileTask {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_compile_task")
    private int id;

    @NotNull
    @Column(name = "init_Code")
    private String initCode;

    @NotNull
    @Column(name = "proper_code")
    private String properCode;

    @NotNull
    @Column(name = "task_content")
    private String taskContent;

    @NotNull
    @Column(name = "points")
    private int points;

    @NotNull
    @Column(name = "inputs")
    private String inputs;
    //endregion

    @ManyToMany
    @JoinTable(
            name = "finished_subsection",
            joinColumns = @JoinColumn(name = "id_course_subsection"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_subsection","id_user"})
    )
    private List<User> userThatFinishedTaskList;

    @ManyToMany
    @JoinTable(
            name = "set_of_compile_tasks",
            joinColumns = @JoinColumn(name = "id_compile_task"),
            inverseJoinColumns = @JoinColumn(name = "id_course_subsection"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_compile_task","id_course_subsection"})
    )
    private List<CourseSubsection> courseSubsectionList;
}
