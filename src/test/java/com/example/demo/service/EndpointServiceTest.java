package com.example.demo.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.EndpointParameterRequest;
import com.example.demo.dto.EndpointRequest;
import com.example.demo.dto.EndpointResponse;
import com.example.demo.model.Endpoint;
import com.example.demo.model.EndpointParameter;
import com.example.demo.repository.EndPointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EndpointServiceTest {

    @Mock
    private EndPointRepository endPointRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EndPointService endPointService;

    private Endpoint endpoint;
    private EndpointResponse endpointResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        endpoint = new Endpoint();
        endpoint.setHttpMethod("GET");

        endpointResponse = new EndpointResponse();
        endpointResponse.setHttpMethod("GET");
    }

    @Test
    void testFindAllEndpoints_WhenHttpMethodIsNull_ShouldReturnAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Endpoint> endpointPage = new PageImpl<>(List.of(endpoint));

        when(endPointRepository.findAll(pageable)).thenReturn(endpointPage);
        when(modelMapper.map(endpoint, EndpointResponse.class)).thenReturn(endpointResponse);

        List<EndpointResponse> result = endPointService.findAllEndpoints(null, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("GET", result.get(0).getHttpMethod());
    }

    @Test
    void testFindAllEndpoints_WhenHttpMethodProvided_ShouldFilterByHttpMethod() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Endpoint> endpointPage = new PageImpl<>(List.of(endpoint));

        // mock repository with specification
        when(endPointRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(endpointPage);
        when(modelMapper.map(endpoint, EndpointResponse.class)).thenReturn(endpointResponse);

        List<EndpointResponse> result = endPointService.findAllEndpoints("GET", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("GET", result.get(0).getHttpMethod());
    }

    @Test
    void testFindAllEndpoints_WhenExceptionOccurs_ShouldReturnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);

        when(endPointRepository.findAll(pageable)).thenThrow(new RuntimeException("DB error"));

        List<EndpointResponse> result = endPointService.findAllEndpoints(null, 0, 10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveEndpoint_withParameters() {
        // Arrange
        EndpointRequest endpointRequest = new EndpointRequest();
        endpointRequest.setEndpointName("TestEndpoint");

        EndpointParameterRequest paramRequest = new EndpointParameterRequest();
        paramRequest.setCustomerParam("param1");
        paramRequest.setVendorParam("value1");
        endpointRequest.setParameters(List.of(paramRequest));

        Endpoint mappedEndpoint = new Endpoint();
        mappedEndpoint.setEndpointName("TestEndpoint");

        EndpointParameter mappedParameter = new EndpointParameter();
        mappedParameter.setCustomerParam("param1");
        mappedParameter.setVendorParam("value1");

        when(modelMapper.map(endpointRequest, Endpoint.class)).thenReturn(mappedEndpoint);
        when(modelMapper.map(paramRequest, EndpointParameter.class)).thenReturn(mappedParameter);
        when(endPointRepository.save(mappedEndpoint)).thenReturn(mappedEndpoint);

        // Act
        Endpoint result = endPointService.saveEndpoint(endpointRequest);

        // Assert
        assertNotNull(result);
        assertEquals("TestEndpoint", result.getEndpointName());
        assertEquals(1, result.getParameters().size());
        assertEquals("param1", result.getParameters().get(0).getCustomerParam());

        verify(endPointRepository, times(1)).save(mappedEndpoint);
    }

    @Test
    void testSaveEndpoint_nullRequest() {
        // Act
        Endpoint result = endPointService.saveEndpoint(null);

        // Assert
        assertNull(result);
        verifyNoInteractions(endPointRepository);
    }

    @Test
    void testSaveEndpoint_withNoParameters() {
        // Arrange
        EndpointRequest endpointRequest = new EndpointRequest();
        endpointRequest.setEndpointName("NoParamEndpoint");

        Endpoint mappedEndpoint = new Endpoint();
        mappedEndpoint.setEndpointName("NoParamEndpoint");

        when(modelMapper.map(endpointRequest, Endpoint.class)).thenReturn(mappedEndpoint);
        when(endPointRepository.save(mappedEndpoint)).thenReturn(mappedEndpoint);

        // Act
        Endpoint result = endPointService.saveEndpoint(endpointRequest);

        // Assert
        assertNotNull(result);
        assertEquals("NoParamEndpoint", result.getEndpointName());
        assertTrue(result.getParameters() == null || result.getParameters().isEmpty());

        verify(endPointRepository, times(1)).save(mappedEndpoint);
    }

    @Test
    void testSaveEndpoint_whenRepositoryThrowsException() {
        // Arrange
        EndpointRequest endpointRequest = new EndpointRequest();
        endpointRequest.setEndpointName("ExceptionEndpoint");

        Endpoint mappedEndpoint = new Endpoint();
        mappedEndpoint.setEndpointName("ExceptionEndpoint");

        when(modelMapper.map(endpointRequest, Endpoint.class)).thenReturn(mappedEndpoint);
        when(endPointRepository.save(mappedEndpoint)).thenThrow(new RuntimeException("DB error"));

        // Act
        Endpoint result = endPointService.saveEndpoint(endpointRequest);

        // Assert
        assertNull(result, "Expected null when repository throws exception");
        verify(endPointRepository, times(1)).save(mappedEndpoint);
    }
}
