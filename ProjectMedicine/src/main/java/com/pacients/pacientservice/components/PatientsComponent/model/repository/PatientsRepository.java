package com.pacients.pacientservice.components.PatientsComponent.model.repository;

import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientsRepository  extends JpaRepository<PatientsTable, Integer> {
    List<PatientsTable> findAll();
    PatientsTable findPatientsTableByCnp(String cnp);
    PatientsTable findPatientsTableByIdUser(Integer idUser);
    List<PatientsTable> findPatientsTableByLastnameContainingAndFirstnameContaining(String firstname, String lastname);
    List<PatientsTable> findPatientsTableByFirstnameContaining(String firstname);
    List<PatientsTable> findPatientsTableByLastnameContaining(String lastname);
    List<PatientsTable> findPatientsTableByEmailContaining(String email);
    PatientsTable findPatientsTableByEmail(String email);
    PatientsTable findPatientsTableByPhone(String phone);
    List<PatientsTable> findPatientsTableByBirthdate(Date birthdate);

}
