package com.pacients.pacientservice.components.AppointmentsComponent.hateoas.PatientOrDoctorInfo;

import com.pacients.pacientservice.components.AppointmentsComponent.controller.AppointmentsController;
import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class AppointmentsHateoasPatientOrDoctorRequest implements RepresentationModelAssembler<AppointmentsTable, EntityModel<AppointmentsTable>> {
    @Override
    public EntityModel<AppointmentsTable> toModel(AppointmentsTable appointment) {
        EntityModel<AppointmentsTable> appointmentModel = EntityModel.of(
                appointment, //entitatea
                linkTo(methodOn(AppointmentsController.class)
                        .getAllAppointments(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        )
                ) .withSelfRel(),

                linkTo(methodOn(AppointmentsController.class)
                        .getAllAppointments(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        )
                ) .withRel("parent")
        );
        return appointmentModel;
    }

}