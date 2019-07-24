package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "materials")
@Data
public class Material {

    //region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_material")
    private int id;

    @NotNull
    @Column(name = "id_material_type")
    private int idMaterialType;

    @NotNull
    @Length(max = 200)
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "width")
    private int width;

    @NotNull
    @Column(name = "height")
    private int height;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_material")
    private List<MaterialPosition> materialPositionList;

    //endregion
}
