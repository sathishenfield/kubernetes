package com.example.demo.controller;

import com.example.demo.dto.EndpointRequest;
import com.example.demo.dto.EndpointResponse;
import com.example.demo.model.Endpoint;
import com.example.demo.service.EndPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aggregate")
@Slf4j
public class AggregateController {

    @Autowired
    private EndPointService endPointService;

    @GetMapping("/get/{path}")
    public ResponseEntity<String> getAggregate(@PathVariable("path") String path){
        log.info(path);
        return ResponseEntity.ok("Success Modified");
    }

    @GetMapping("/endpoints")
    public ResponseEntity<List<EndpointResponse>> getEndpoints(@RequestParam("httpMethod") String httpMethod){
        List<EndpointResponse> endpointResponses = endPointService.findAllEndpoints(httpMethod,0,10);
        return ResponseEntity.ok(endpointResponses);
    }

    @PostMapping("/endpoints/save")
    public ResponseEntity<Endpoint> saveEndpoints(@RequestBody EndpointRequest endpointRequest){
        Endpoint endpoint = endPointService.saveEndpoint(endpointRequest);
        return ResponseEntity.ok(endpoint);
    }

}
