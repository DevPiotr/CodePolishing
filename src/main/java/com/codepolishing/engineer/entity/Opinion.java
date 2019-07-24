package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "opinions")
@Data
public class Opinion {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_opinion")
    private int id;

    @NotNull
    @Length(max = 200)
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "id_user")
    private int idUser;

    @NotNull
    @Column(name = "id_course_subsection")
    private int idCourseSubsection;

    @NotNull
    @Column(name = "opinion_rate")
    private int opinionRate;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    //endregion
}
