package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VendorResponse {
    private Integer id;
    private String vendorName;
    private String baseUrl;
    private String description;
    private LocalDateTime createdAt;
    private List<EndpointResponse> endpoints;
    private String category;
}
