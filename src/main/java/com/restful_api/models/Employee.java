package com.restful_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Entity
@Table(name = "employees", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "phone"})})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column
    @NotBlank(message = "Name is required")
    private String name;

    @Column()
    private String email;
    @Column()
    @NotBlank(message = "phone is required")
    private String phone;
    @Column
    @NotBlank(message = "Address is required")
    private String address;

    @ManyToOne
    private Department department;



}
