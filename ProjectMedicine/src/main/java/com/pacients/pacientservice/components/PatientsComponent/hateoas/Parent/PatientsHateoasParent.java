package com.pacients.pacientservice.components.PatientsComponent.hateoas.Parent;

import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class PatientsHateoasParent implements RepresentationModelAssembler<PatientsTable, EntityModel<PatientsTable>> {
    @Override
    public EntityModel<PatientsTable> toModel(PatientsTable patient)
    {
        EntityModel<PatientsTable> pacientModel = EntityModel.of(
                patient, //entitatea
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
