package com.example.demo.repository;

import com.example.demo.model.EndpointParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EndPointParameterRepository extends JpaRepository<EndpointParameter, Integer>, JpaSpecificationExecutor<EndpointParameter> {
}
