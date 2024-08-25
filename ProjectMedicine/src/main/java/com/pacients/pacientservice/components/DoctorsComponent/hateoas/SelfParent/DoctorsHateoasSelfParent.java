package com.pacients.pacientservice.components.DoctorsComponent.hateoas.SelfParent;

import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
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
public class DoctorsHateoasSelfParent implements RepresentationModelAssembler<DoctorsTable, EntityModel<DoctorsTable>> {
    @Override
    public EntityModel<DoctorsTable> toModel(DoctorsTable doctor)
    {
        EntityModel<DoctorsTable> doctorModel = EntityModel.of(
                doctor, //entitatea
                linkTo(methodOn(DoctorsController.class)
                        .getDoctorByIdDoctor(
                                doctor.getIdDoctor()
                        )
                ).withSelfRel(), //link catre resursa mea pacient
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

    public ResponseEntity<?> provideHeateoasForPageable( Integer itemsPerPage)
    {

        /* Code like that because fields don't exist in doctorTable -> difficult to introduce in DoctorsHateoasSelfParentCustom*/
        Link nextLink = linkTo(methodOn(DoctorsController.class)
                .getAllDoctors(Optional.of(0),
                        Optional.ofNullable(itemsPerPage),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()))
                .withRel("next");
        Link parent = WebMvcLinkBuilder.linkTo(methodOn(DoctorsController.class)
                        .getAllDoctors(Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()))
                .withRel("parent");

        ArrayList<Link> array = new ArrayList<>();
        array.add(parent);
        array.add(nextLink);

        Map<String, ArrayList<Link>> links = new HashMap<>();
        links.put("_links", array);

        return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);

    }
}