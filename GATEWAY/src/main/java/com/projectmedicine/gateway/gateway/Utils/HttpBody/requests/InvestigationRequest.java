package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestigationRequest {
    private String _id;
    private List<Investigation> investigations;
}