package com.example.demo.repository;

import com.example.demo.model.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EndPointRepository extends JpaRepository<Endpoint, Integer>, JpaSpecificationExecutor<Endpoint> {
}
