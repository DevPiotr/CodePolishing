package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "opinions")
@Data
public class Opinion {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opinion")
    private int id;

    @NotNull
    @Column(name = "content")
    private String content;

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
