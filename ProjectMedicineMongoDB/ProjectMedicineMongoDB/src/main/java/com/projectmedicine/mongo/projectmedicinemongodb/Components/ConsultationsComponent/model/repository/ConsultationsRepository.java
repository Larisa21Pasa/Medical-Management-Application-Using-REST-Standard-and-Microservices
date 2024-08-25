package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.repository;


import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.InvestigationsCollection;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationsRepository extends MongoRepository<ConsultationsCollection, String> {

    ConsultationsCollection findConsultationsCollectionBy_id(String _id);
    List<ConsultationsCollection> findConsultationsCollectionsByIdDoctor(Integer idDoctor);
    List<ConsultationsCollection> findConsultationsCollectionsByIdPatient(String idPatient);
    List<ConsultationsCollection> findConsultationsCollectionsByAppointmentTime(LocalDateTime time);
    List<ConsultationsCollection> findConsultationsCollectionByDiagnostic(String diagnostic);

    ConsultationsCollection findConsultationsCollectionByIdDoctorAndIdPatientAndAppointmentTime(int idDoctor, String idPacient, LocalDateTime appointmentTime);
    //List<ConsultationsCollection> findByInvestigations_Id(Integer id);
    List<ConsultationsCollection> findByInvestigations_Naming(String naming);
    List<ConsultationsCollection> findByInvestigations_ProcessingHours(int processingHours);
    List<ConsultationsCollection> findByInvestigations_Result(String result);
}
