package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.Parent;

import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.controller.ConsultationsController;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConsultationsHateoasParent implements RepresentationModelAssembler<ConsultationsCollection, EntityModel<ConsultationsCollection>>
{
        @Override
        public EntityModel<ConsultationsCollection> toModel(ConsultationsCollection consultation)
        {
            //TODO SA SCHIMB PE CNP SAU PE IDUSER HATEOAS UL?
            // DE MODIFICAT PENTRU PAGINARE
            EntityModel<ConsultationsCollection> consultationModel = EntityModel.of(
                    consultation, //entitatea
                    linkTo(methodOn(ConsultationsController.class)
                            .getAllConsultations(
                                    Optional.empty(),
                                    Optional.empty(),
                                    Optional.empty(),
                                    Optional.empty(),
                                    Optional.empty(),
                                    Optional.empty()))
                            .withRel("parent") //link catre toate resursele
            );
            return consultationModel;
        }

}
