package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "endpoint")
public class Endpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "endpoint_name", nullable = false)
    private String endpointName;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "endpoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EndpointParameter> parameters;
}
