package com.example.demo.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.dto.EndpointResponse;
import com.example.demo.model.Endpoint;
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
}
