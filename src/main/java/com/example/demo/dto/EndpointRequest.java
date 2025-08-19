package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EndpointRequest {
    private String endpointName;
    private String httpMethod;
    private String description;
    private List<EndpointParameterRequest> parameters;
    private Integer vendorId;
}
