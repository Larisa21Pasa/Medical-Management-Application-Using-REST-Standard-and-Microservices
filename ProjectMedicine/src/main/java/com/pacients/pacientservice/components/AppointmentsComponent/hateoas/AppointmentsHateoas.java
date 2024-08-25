package com.pacients.pacientservice.components.AppointmentsComponent.hateoas;

import com.pacients.pacientservice.components.AppointmentsComponent.controller.AppointmentsController;
import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//DE MANAGERIAT CAZ PENTRU SELF+P SI DOAR P
@Component
public class AppointmentsHateoas implements RepresentationModelAssembler<AppointmentsTable, EntityModel<AppointmentsTable>> {
    @Override
    public EntityModel<AppointmentsTable> toModel(AppointmentsTable appointment)
    {
        EntityModel<AppointmentsTable> appointmentModel = EntityModel.of(appointment,
                //TODO SA VAD PE HATEOAS -> VINE RESURSA TOTALA, INSA PE "SELF LINK" CUM O ALEG : PACIENT SAU DOCTOR
                linkTo(methodOn(AppointmentsController.class).getDoctorAppointments(appointment.getAppointmentsTablePK().getIdDoctor(),Optional.empty(),Optional.empty(),Optional.empty()))
                        .withSelfRel(), //resursa self specifica doctorului
                linkTo(methodOn(AppointmentsController.class).getPatientAppointments(appointment.getAppointmentsTablePK().getCnp(),Optional.empty(),Optional.empty(),Optional.empty()))
                        .withSelfRel(), //resursa self specifica doctorului
                linkTo(methodOn(AppointmentsController.class).getAllAppointments(Optional.empty(),Optional.empty(),Optional.empty()))
                        .withRel("parent") //resursa parinte cu toate informatiile
        );
        return appointmentModel;

    }
}
