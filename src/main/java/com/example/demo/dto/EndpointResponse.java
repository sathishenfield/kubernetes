package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EndpointResponse {
    private Integer id;
    private String endpointName;
    private String httpMethod;
    private String description;
    private List<EndpointParameterResponse> parameters;
    private Integer vendorId;
}
