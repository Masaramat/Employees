package com.restful_api.controllers;

import com.restful_api.models.Department;
import com.restful_api.services.interfaces.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping(value = "/department/create")
    public ResponseEntity<?> createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }

    @GetMapping(value = "/departments")
    public ResponseEntity<?> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @DeleteMapping(value = "department/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
        return departmentService.deleteDepartment(id);

    }
}
