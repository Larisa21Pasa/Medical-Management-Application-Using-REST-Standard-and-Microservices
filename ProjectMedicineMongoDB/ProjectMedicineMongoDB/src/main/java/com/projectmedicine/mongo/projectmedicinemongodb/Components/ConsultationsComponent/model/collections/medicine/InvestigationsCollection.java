package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonRootName(value = "investigation")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvestigationsCollection {
    @Id

    private String _id = String.valueOf(new ObjectId());

    private String naming;
    private int processingHours;
    private String result;

}
