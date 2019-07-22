package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @NotNull
    @Max(value = 11)
    @Column(name = "score")
    private int score;

    @NotNull
    @Max(value = 100)
    @Column(name = "password")
    private String password;

    @NotNull
    @Max(value = 35)
    @Column(name = "name")
    private String name;

    @NotNull
    @Max(value = 50)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Max(value = 50)
    @Column(name = "city")
    private String city;

    @Max(value = 50)
    @Column(name = "street")
    private String street;

    @NotNull
    @Max(value = 15)
    @Column(name = "house_number")
    private String houseNumber;

    @Max(value = 5)
    @Column(name = "flat_nr")
    private String flatNr;

    @NotNull
    @Max(value = 6)
    @Column(name = "post_code")
    private String postCode;

    @NotNull
    @Max(value = 30)
    @Column(name = "email")
    private String email;

    @Max(value = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "cv_path")
    private String CVPath;

    @ManyToMany
    @JoinTable(
            name = "personalized_job_offers",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_job_offer"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_job_offer","id_user"})
    )
    private List<JobOffer> jobOfferList;

    @ManyToMany
    @JoinTable(
            name = "personalized_course_types",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course_type"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_type","id_user"})
    )
    private List<CourseType> courseTypeList;

    @ManyToMany
    @JoinTable(
            name = "joining_to_sections",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course_section"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_section","id_user"})
    )
    private List<CourseSection> courseSectionList;

    @OneToMany
    @JoinTable(name = "id_user")
    private List<Opinion> opinionList;
    //endregion From


}
