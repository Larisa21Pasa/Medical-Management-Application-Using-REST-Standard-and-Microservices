package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Investigation {
    private String naming;
    private String processingHours;
    private String result;
}
