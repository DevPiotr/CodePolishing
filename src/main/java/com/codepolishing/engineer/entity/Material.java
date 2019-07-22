package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "materials")
@Data
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private int id;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "width")
    private int width;

    @NotNull
    @Column(name = "height")
    private int height;

    @OneToMany
    @JoinTable(name = "id_material")
    private List<MaterialPosition> materialPositionList;
}
