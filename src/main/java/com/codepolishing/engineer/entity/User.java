package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private int Id;

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
    @Column(name = "housenumber")
    private String houseNumber;

    @Max(value = 5)
    @Column(name = "flatnr")
    private String flatNr;

    @NotNull
    @Max(value = 6)
    @Column(name = "postcode")
    private String postCode;

    @NotNull
    @Max(value = 30)
    @Column(name = "email")
    private String email;

    @Max(value = 12)
    @Column(name = "phonenumber")
    private String phoneNumber;

    @NotNull
    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "CVPath")
    private String CVPath;
    //endregion From
}
