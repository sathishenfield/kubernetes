package com.example.demo.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class Request2 {

    public String NAME;

    public String Email;

    public Integer age;

    public boolean CHECK;

    @Override
    public String toString() {
        return "Request2{" +
                "NAME='" + NAME + '\'' +
                ", Email='" + Email + '\'' +
                ", age=" + age +
                ", CHECK=" + CHECK +
                '}';
    }
}
