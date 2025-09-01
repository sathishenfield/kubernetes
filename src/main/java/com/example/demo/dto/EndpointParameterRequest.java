package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndpointParameterRequest {
    private String customerParam;
    private String vendorParam;
    private String description;
    private Integer endpointId;
}
