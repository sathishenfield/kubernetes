package com.example.demo.service;

import com.example.demo.dto.EndpointRequest;
import com.example.demo.dto.EndpointResponse;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointParameter;
import com.example.demo.repository.EndPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EndPointService {

    @Autowired
    private EndPointRepository endPointRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Endpoint saveEndpoint(EndpointRequest endpointRequest)  {
        log.info("saveEndpoint: Request started.");

        if (Objects.isNull(endpointRequest)) {
            log.error("saveEndpoint: EndpointRequest is null. Requests: {}", endpointRequest);
        }

        try {
            Endpoint endpoint = modelMapper.map(endpointRequest, Endpoint.class);
            log.debug("Mapped EndpointRequest to Endpoint: {}", endpoint);

            if (Objects.nonNull(endpointRequest.getParameters())) {
                log.debug("Mapping {} parameters...", endpointRequest.getParameters().size());

                List<EndpointParameter> parameterList = endpointRequest.getParameters().stream()
                        .map(param -> {
                            EndpointParameter parameter = modelMapper.map(param, EndpointParameter.class);
                            parameter.setEndpoint(endpoint);
                            log.debug("Mapped parameter: {}", parameter);
                            return parameter;
                        }).collect(Collectors.toList());

                endpoint.setParameters(parameterList);
            }
            Endpoint saved = endPointRepository.save(endpoint);
            log.info("Saved endpoint to database: {}", saved);
            return saved;

        } catch (Exception e) {
            log.error("saveEndpoint: Error while saving Endpoint. Request: {}", endpointRequest, e);
        }
        return null;
    }

    public List<EndpointResponse> findAllEndpoints(String httpMethod ,Integer pageNo, Integer pageSize) {
        log.info("EndPointServiceImpl - findAllEndpoints: request started for fetching endpoints");
        Pageable paging = PageRequest.of(pageNo, pageSize);

        boolean isFilterEmpty = (httpMethod == null || httpMethod.isBlank());

        Page<Endpoint> endpoints;
        try {
            if (isFilterEmpty) {
                endpoints = endPointRepository.findAll(paging);
            } else {
                Specification<Endpoint> spec = (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.equal(root.get("httpMethod"), httpMethod));
                    return cb.and(predicates.toArray(new Predicate[0]));
                };
                endpoints = endPointRepository.findAll(spec, paging);
            }
            List<EndpointResponse> endpointResponses = new ArrayList<>();
            for (Endpoint endpoint : endpoints.getContent()) {
                EndpointResponse endpointResponse = convertToResponse(endpoint);
                endpointResponses.add(endpointResponse);
            }
            log.info("EndPointServiceImpl - findAllEndpoints: Successfully fetched endpoint(s) details.");
            return endpointResponses;
        } catch (Exception e) {
            log.error("Error while fetching endpoint details", e);
        }
        return List.of();
    }

    private EndpointResponse convertToResponse(Endpoint endpoint) {
        try {
            return modelMapper.map(endpoint, EndpointResponse.class);
        } catch (Exception e) {
            log.error("EndPointServiceImpl - convertToResponse: Failed to map Endpoint entity to EndpointResponse. Error: {}", e.getMessage(), e);
        }
        return null;
    }
}
