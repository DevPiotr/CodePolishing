package com.codepolishing.engineer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "course_sections")
@Data
public class CourseSection {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course_section")
    private int id;

    @NotNull
    @Column(name = "id_course")
    private int idCourse;

    @NotNull
    @Length(max = 30)
    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @NotNull
    @Column(name = "section_part")
    private int sectionPart;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course_section")
    private List<CourseSubsection> courseSubsectionList;

    @ManyToMany
    @JoinTable(
            name = "joining_to_sections",
            joinColumns = @JoinColumn(name = "id_course_section"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_section","id_user"})
    )
    private List<User> joinedUserList;

    //endregion
}
