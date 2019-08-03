package com.codepolishing.engineer.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private int id;

    @NotNull
    @Column(name = "id_user_role")
    private int idUserRole;

    @NotNull
    @Column(name = "id_province")
    private int idProvince;

    @NotNull
    @Column(name = "score")
    private int score;

    @NotNull
    @Length(max = 100)
    @Column(name = "password")
    private String password;

    private String confirmPassword;

    @NotNull
    @Length(max = 35)
    @Column(name = "name")
    private String name;

    @NotNull
    @Length(max = 50)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Length(max = 50)
    @Column(name = "city")
    private String city;

    @Length(max = 50)
    @Column(name = "street")
    private String street;

    @NotNull
    @Length(max = 15)
    @Column(name = "house_number")
    private String houseNumber;

    @Length(max = 5)
    @Column(name = "flat_nr")
    private String flatNr;

    @NotNull
    @Length(max = 6)
    @Column(name = "post_code")
    private String postCode;

    @NotNull
    @Length(max = 30)
    @Column(name = "email")
    private String email;

    @Length(max = 12)
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
    @JoinColumn(name = "id_user")
    private List<Opinion> opinionList;

    //endregion From


}
