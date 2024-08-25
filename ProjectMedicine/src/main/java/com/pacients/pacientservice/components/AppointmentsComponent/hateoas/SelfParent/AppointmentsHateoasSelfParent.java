package com.pacients.pacientservice.components.AppointmentsComponent.hateoas.SelfParent;

import com.pacients.pacientservice.components.AppointmentsComponent.controller.AppointmentsController;
import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
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
public class AppointmentsHateoasSelfParent implements RepresentationModelAssembler<AppointmentsTable, EntityModel<AppointmentsTable>> {
    @Override
    public EntityModel<AppointmentsTable> toModel(AppointmentsTable appointment) {
        EntityModel<AppointmentsTable> appointmentModel = EntityModel.of(
                appointment, //entitatea
                linkTo(methodOn(AppointmentsController.class)
                        .getDoctorAppointments(
                                appointment.getAppointmentsTablePK().getIdDoctor(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()))
                        .withSelfRel(), //resursa self specifica doctorului
                linkTo(methodOn(AppointmentsController.class)
                        .getPatientAppointments(
                                appointment.getAppointmentsTablePK().getCnp(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()))
                        .withSelfRel(), //resursa self specifica pacientului
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

    public ResponseEntity<?> provideHeateoasForPageable(Integer itemsPerPage) {

        /* Code like that because fields don't exist in doctorTable -> difficult to introduce in DoctorsHateoasSelfParentCustom*/
        Link nextLink = linkTo(methodOn(AppointmentsController.class)
                .getAllAppointments(Optional.of(0),
                        Optional.ofNullable(itemsPerPage),
                        Optional.empty()
                )
        ).withRel("next");
        Link parent = WebMvcLinkBuilder.linkTo(methodOn(AppointmentsController.class)
                .getAllAppointments(Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                )
        ).withRel("parent");

        ArrayList<Link> array = new ArrayList<>();
        array.add(parent);
        array.add(nextLink);

        Map<String, ArrayList<Link>> links = new HashMap<>();
        links.put("_links", array);

        return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);

    }
}
