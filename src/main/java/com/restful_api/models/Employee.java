package com.restful_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employees", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "phone"})})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
    private int department_id;
    @Column
    @NotBlank(message = "Address is required")
    private String address;



    public Employee() {

    }


    public Employee(int id, String name, String email, String phone, int department_id, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department_id = department_id;
        this.address = address;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
