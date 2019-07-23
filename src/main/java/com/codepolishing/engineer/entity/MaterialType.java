package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "material_types")
@Data
public class MaterialType {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_type")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_material")
    private List<Material> materialList;

    //endregion
}
