package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "theories")
@Data
public class Theory {

    //TODO ManyToMany with extraColumns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_theory")
    private int id;

    @NotNull
    @Column(name = "theory_content")
    private String theoryContent;

    @ManyToMany
    @JoinTable(
            name = "set_of_theories",
            joinColumns = @JoinColumn(name = "id_theory"),
            inverseJoinColumns = @JoinColumn(name = "id_course_subsection"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_theory","id_course_subsection"})
    )
    private List<CourseSubsection> courseSubsectionList;

    @OneToMany
    @JoinTable(name = "id_theory")
    private List<MaterialPosition> materialPositionList;
}
