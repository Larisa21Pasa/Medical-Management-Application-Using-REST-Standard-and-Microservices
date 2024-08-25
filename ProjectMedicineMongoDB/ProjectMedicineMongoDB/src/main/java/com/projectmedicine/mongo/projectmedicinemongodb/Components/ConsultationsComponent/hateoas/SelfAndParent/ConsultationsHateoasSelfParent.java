package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.SelfAndParent;

import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.controller.ConsultationsController;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import lombok.extern.log4j.Log4j2;
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
@Log4j2

@Component
public class ConsultationsHateoasSelfParent implements RepresentationModelAssembler<ConsultationsCollection, EntityModel<ConsultationsCollection>> {
    @Override
    public EntityModel<ConsultationsCollection> toModel(ConsultationsCollection consultation)
    {

        EntityModel<ConsultationsCollection> consultationModel = EntityModel.of(
                consultation, //entitatea
                linkTo(methodOn(ConsultationsController.class).getConsultationById(consultation.get_id()))
                        .withSelfRel(), //link catre resursa mea consultatie
                linkTo(methodOn(ConsultationsController.class).getAllConsultations(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()))
                        .withRel("parent") //link catre toate resursele
        );
        return consultationModel;
    }
    public ResponseEntity<?> provideHeateoasForPageable(Integer itemsPerPage)
    {

        /* Code like that because fields don't exist in doctorTable -> difficult to introduce in DoctorsHateoasSelfParentCustom*/
        Link nextLink = linkTo(methodOn(ConsultationsController.class)
                .getAllConsultations(Optional.of(0),
                        Optional.ofNullable(itemsPerPage),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                )
        ).withRel("next");
        Link parent = WebMvcLinkBuilder.linkTo(methodOn(ConsultationsController.class)
                        .getAllConsultations(Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
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
