package com.restful_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_view")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeView extends Employee{
    private String department;
    public EmployeeView(int id, String name, String email, String phone, String department, String address, int department_id) {
        super(id, name, email, phone, department_id, address);
        this.department = department;
    }


}
