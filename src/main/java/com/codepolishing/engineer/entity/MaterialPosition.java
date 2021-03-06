package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "material_positions")
@Data
public class MaterialPosition {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_material_position")
    private int id;

    @NotNull
    @Column(name = "id_material")
    private int idMaterial;

    @NotNull
    @Column(name = "id_theory")
    private int idTheory;

    @NotNull
    @Column(name = "position")
    private int position;

    //endregion
}
