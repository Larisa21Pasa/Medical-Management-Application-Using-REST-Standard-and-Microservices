package com.pacients.pacientservice.components.DoctorsComponent.hateoas.Parent;

import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class DoctorsHateoasParent implements RepresentationModelAssembler<DoctorsTable, EntityModel<DoctorsTable>> {
    @Override
    public EntityModel<DoctorsTable> toModel(DoctorsTable doctor)
    {
        EntityModel<DoctorsTable> doctorModel = EntityModel.of(
                doctor, //entitatea
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
