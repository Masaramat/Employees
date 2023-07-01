package com.restful_api.controllers;

import com.restful_api.models.Department;
import com.restful_api.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class DepartmentController {
    @Autowired
    private DepartmentRepository repository;
    @PostMapping(value = "/department/create")
    public ResponseEntity<?> createDepartment(@RequestBody Department department){
        Department saved = repository.save(department);
        if (saved == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add department");
        }
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping(value = "/departments")
    public ResponseEntity<?> getAllDepartment(){
        return ResponseEntity.ok().body(repository.findAll());
    }

    @DeleteMapping(value = "department/delete/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Integer id){
        if(!repository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
        }
        String deletedDept = repository.findById(id).get().getName();

        return ResponseEntity.status(204).body(deletedDept + " deleted successfully");
    }
}
