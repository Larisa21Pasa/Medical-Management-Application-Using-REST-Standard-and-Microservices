package com.pacients.pacientservice.components.DoctorsComponent.hateoas.Custom;

import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@Component
public class DoctorsHateoasSelfParentCustom implements RepresentationModelAssembler<DoctorsTable, EntityModel<DoctorsTable>> {

    @Override
    public EntityModel<DoctorsTable> toModel(DoctorsTable doctor)
    {
        log.info("toModel ==> {} {} {}", doctor, Optional.ofNullable(doctor.getLastname()), Optional.ofNullable(doctor.getSpecialization()));
        EntityModel<DoctorsTable> doctorModel = EntityModel.of(
                doctor, //entitatea
                linkTo(methodOn(DoctorsController.class)
                        .getAllDoctors(
                            Optional.empty(),
                                Optional.empty(),
                                Optional.ofNullable(doctor.getLastname()),
                                Optional.ofNullable(doctor.getFirstname()),
                                Optional.ofNullable(doctor.getEmail()),
                                Optional.ofNullable(doctor.getPhone()),
                                Optional.ofNullable(doctor.getSpecialization())
                        )
                ).withSelfRel(), //link catre resursa mea
                linkTo(methodOn(DoctorsController.class)
                        .getAllDoctors(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        )
                ).withRel("parent") //link catre toate resursele
        );
        return doctorModel;
    }
}
