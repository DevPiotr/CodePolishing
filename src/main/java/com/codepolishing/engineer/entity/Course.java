package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "courses")
@Data
public class Course {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int Id;

    @NotNull
    @Max(value = 100)
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
    //endregion
}
