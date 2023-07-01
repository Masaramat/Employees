package com.restful_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "departments", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Name is required and Unique")
    private String name;
    private String description;
}
