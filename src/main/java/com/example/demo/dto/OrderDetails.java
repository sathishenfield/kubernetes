package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetails {

    private BigDecimal amount;

    private Integer customerId;

    private String email;
}
