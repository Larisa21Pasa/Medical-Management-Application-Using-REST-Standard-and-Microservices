package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.service.Consultations;


import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.InvestigationsCollection;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationsService {
    //GET
    CollectionModel<EntityModel<ConsultationsCollection>> getAllConsultations();

    EntityModel<ConsultationsCollection> getCollectionById(String cnp);

    CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByIdPatient(String idPacient);

    CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByIdDoctor(Integer idDoctor);

    CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByAppointmentPK(LocalDateTime appointmentTime);

    CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByDiagnostic(String diagnostic);
     CollectionModel<EntityModel<ConsultationsCollection>> intersect(
            CollectionModel<EntityModel<ConsultationsCollection>> result1,
            CollectionModel<EntityModel<ConsultationsCollection>> result2);
    CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByNumberPage(Integer page, Integer itemsPerPage) ;

    //PUT
    EntityModel<ConsultationsCollection> addConsultation(ConsultationsCollection consultation);

    EntityModel<ConsultationsCollection> addInvestigation(ConsultationsCollection consultation);

    EntityModel<ConsultationsCollection> updateConsultation(ConsultationsCollection consultationsCollection, String objectId);

    //DELETE
    EntityModel<ConsultationsCollection> deleteConsultation(String _id);
}