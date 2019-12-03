package com.codepolishing.engineer.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "job_offers")
@Data
public class JobOffer {

    // region Fields From Database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_job_offer")
    private int id;

    @NotNull
    @Length(max = 40)
    @Column(name = "company_name")
    private String companyName;

    @Email
    @Column(name = "employer_email")
    private String employerEmail;

    @NotNull
    @Column(name = "job_offer_content")
    private String jobOfferContent;

    @Column(name = "technology")
    private String technology;

    @Column(name = "short_description")
    private String shortDescription;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    @ManyToMany
    @JoinTable(
            name = "personalized_job_offers",
            joinColumns = @JoinColumn(name = "id_job_offer"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_job_offer","id_user"})
    )
    private List<User> userList;

    //endregion

}
