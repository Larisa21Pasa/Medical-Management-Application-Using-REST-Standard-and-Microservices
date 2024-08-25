package com.pacients.pacientservice.components.DoctorsComponent.model.repository;

import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository  extends CrudRepository<DoctorsTable, Integer> {
    List<DoctorsTable> findAll();
    DoctorsTable findDoctorsTableByIdDoctor(Integer idDoctor);
    DoctorsTable findDoctorsTableByIdUser(Integer idUser);
    List<DoctorsTable> findDoctorsTableByLastnameContainingAndFirstnameContaining(String firstname, String lastname);
    List<DoctorsTable> findDoctorsTableByFirstnameContaining(String firstname);
    List<DoctorsTable> findDoctorsTableByLastnameContaining(String lastname);
    List<DoctorsTable> findDoctorsTableBySpecializationContaining(String specialization);

    List<DoctorsTable> findDoctorsTableByEmailContaining(String email);
    DoctorsTable findDoctorsTableByEmail(String email);
    DoctorsTable findDoctorsTableByPhone(String phone);
    List<DoctorsTable> findDoctorsTableBySpecialization(String specialization);


}
