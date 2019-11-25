package com.codepolishing.engineer.entity;

import com.codepolishing.engineer.validAnnotation.FieldMatch;
import com.codepolishing.engineer.validAnnotation.UniqueEmail;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "Hasła różnią się")
public class User implements UserDetails {

    //region Fields From Database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @NotNull
    @Column(name = "id_user_role")
    private int idUserRole;

    @NotNull
    @Column(name = "id_province")
    private int idProvince;

    @NotNull(message = "blad")
    @Column(name = "score")
    private int score;

    @NotNull
    @Length(max = 100)
    @Column(name = "password")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Hasło musi zawierac conajmniej jedną duża litere, cyfre i mieć minimalną długość 8 znaków" )
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull(message = "Imię: pole wymagane")
    @Length(max = 35)
    @Column(name = "name")
    private String name;

    @NotNull(message = "Nazwisko: pole wymagane")
    @Length(max = 50)
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Miasto: pole wymagane")
    @Length(max = 50)
    @Column(name = "city")
    private String city;

    @Length(max = 50)
    @Column(name = "street")
    private String street;

    @NotNull(message = "Numer domu: pole wymagane")
    @Length(max = 15)
    @Column(name = "house_number")
    private String houseNumber;

    @Length(max = 5)
    @Column(name = "flat_nr")
    private String flatNr;

    @NotNull
    @Length(max = 6)
    @Column(name = "post_code")
    @Pattern(regexp = "[0-9]{2}\\-[0-9]{3}", message = "Podaj kod pocztowy w formcie NN-NNN")
    private String postCode;

    @NotNull()
    @UniqueEmail()
    @NotBlank(message = "Niepoprawny email")
    @Length(max = 30)
    @Column(name = "email")
    @Email(message = "Niepoprawny email")
    private String email;

    @Length(max = 12, message = "Telefon: nr. tel. nie może przekraczać 12 znaków")
    @Length(min = 9, message = "Telefon: nr. tel. nie może być krótszy niż 9 znaków")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "Data urodzenia: podaj poprawny format")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "cv_path")
    private String CVPath;

    @Column(name = "image")
    private byte[] image;

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

    @ManyToMany
    @JoinTable(
            name = "joining_to_courses",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course","id_user"})
    )
    private List<Course> courseList;

    @ManyToMany
    @JoinTable(
            name = "finished_subsection",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course_subsection"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course_subsection","id_user"})
    )
    private List<CourseSubsection> finishedSubsectionList;

    @OneToMany
    @JoinColumn(name = "id_user")
    @ToString.Exclude
    private List<Opinion> opinionList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    //endregion From
}
