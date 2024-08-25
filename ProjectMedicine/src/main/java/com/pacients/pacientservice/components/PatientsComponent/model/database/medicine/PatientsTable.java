package com.pacients.pacientservice.components.PatientsComponent.model.database.medicine;


import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientsTable {
    @Id
    @Column(name = "cnp")
    private String cnp;

    private Integer idUser;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "isactive")
    private Boolean isactive;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<AppointmentsTable> appointmentsTableSet= new HashSet<>();

    public PatientsTable(String cnp, Integer idUser, String lastname, String firstname, String email, String phone, Boolean isactive)
    {
        this.cnp = cnp;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.isactive = isactive;
        this.idUser = idUser;
    }
}
