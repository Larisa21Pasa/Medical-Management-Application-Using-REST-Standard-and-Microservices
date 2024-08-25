package com.pacients.pacientservice.components.PatientsComponent.hateoas.Custom;

import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class PatientsHateoasSelfParentCustom implements RepresentationModelAssembler<PatientsTable, EntityModel<PatientsTable>> {

    @Override
    public EntityModel<PatientsTable> toModel(PatientsTable patient)
    {
        EntityModel<PatientsTable> pacientModel = EntityModel.of(
                patient, //entitatea
                linkTo(methodOn(PatientsController.class)
                        .getAllPatients(
                                Optional.ofNullable(patient.getLastname()),
                                Optional.ofNullable(patient.getFirstname()),
                                Optional.ofNullable(patient.getEmail()),
                                Optional.ofNullable(patient.getPhone()),
                                Optional.ofNullable(patient.getBirthdate())
                        )
                ).withSelfRel(), //link catre resursa mea pacient
                linkTo(methodOn(PatientsController.class)
                        .getAllPatients(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        )
                ).withRel("parent") //link catre toate resursele
        );
        return pacientModel;
    }
}
