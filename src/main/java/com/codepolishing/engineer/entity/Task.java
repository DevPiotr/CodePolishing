package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_task")
    private int id;

    @NotNull
    @Column(name = "task_content")
    private String taskContent;

    @NotNull
    @Length(max = 45)
    @Column(name = "right_answer")
    private String rightAnswer;

    @Column(name = "all_answers")
    private String allAnswers;

    @ManyToMany
    @JoinTable(
            name = "set_of_tasks",
            joinColumns = @JoinColumn(name = "id_task"),
            inverseJoinColumns = @JoinColumn(name = "id_course_subsection"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_task","id_course_subsection"})
    )
    private List<CourseSubsection> courseSubsectionList;

    //endregion

}
