package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRequest {
    private String vendorName;
    private String baseUrl;
    private String description;
    private String category;
}
