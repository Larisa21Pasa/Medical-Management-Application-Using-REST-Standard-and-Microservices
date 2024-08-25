package com.pacients.pacientservice.components.AppointmentsComponent.hateoas.Custom;

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




@Component
public class AppointmentsHateoasSelfParentCustom implements RepresentationModelAssembler<AppointmentsTable, EntityModel<AppointmentsTable>> {

    @Override
    public EntityModel<AppointmentsTable> toModel(AppointmentsTable appointment)
    {
        EntityModel<AppointmentsTable> appointmentModel = EntityModel.of(
                appointment, //entitatea
                linkTo(methodOn(AppointmentsController.class)
                        .getAllAppointments(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.ofNullable(appointment.getStatus())
                        )
                ).withSelfRel(), //link catre resursa mea
                linkTo(methodOn(AppointmentsController.class)
                        .getAllAppointments(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        )
                ).withRel("parent") //link catre toate resursele
        );
        return appointmentModel;
    }
}
