
package com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine;


import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsTable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_doctor")
    private Integer idDoctor;

    private Integer idUser;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "specialization")
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    Set<AppointmentsTable> appointmentsTableSet = new HashSet<>();

    public DoctorsTable( Integer idUser, String lastname, String firstname, String email, String phone, String specialization)
    {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;

        this.idUser = idUser;

    }
}
