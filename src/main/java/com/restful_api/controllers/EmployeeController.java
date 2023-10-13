package com.restful_api.controllers;

import com.restful_api.models.Employee;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.services.interfaces.EmployeeService;
import com.restful_api.specifications.EmployeeSpecification;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Most or all logic are handled here because extensive logic is not required by the APIs
@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping(value = "/employees")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }
    // Gets all employees
    @GetMapping(value = "/employees")
    public ResponseEntity<?> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping(value = "/employees/page/{page}/size/{size}")
    public ResponseEntity<?> GetPaginatedEmployees(@PathVariable int page, @PathVariable int size){
        return employeeService.GetPaginatedEmployees(page, size);
    }

    // Gets list of employees and sort by given parameter
    @GetMapping(value = "/employees/sort/{sortBy}/{sortType}")
    public ResponseEntity<?> getSortedEmployees(
            @PathVariable String sortBy,
            @PathVariable String sortType){
        return employeeService.getSortedEmployees(sortBy, sortType);
    }


    // Gets an employee by id
    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){

        return employeeService.getEmployee(id);
    }
    // Implements search employee by any attribute. this uses the Like keyword so you can implement dynamic search
    // on your UI
    //Posible to use Pagination to implement a simple form of this request
    @GetMapping(value = "/employees/search/{attribute}/{value}")
    public List<Employee> searchEmployees(
            @PathVariable("attribute") String attribute,
            @PathVariable("value") String value) {
        return employeeService.searchEmployees(attribute, value);
    }

    // Update employee with certain ID
    @PutMapping(value = "/employee/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) throws Exception {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping(value = "employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        return employeeService.deleteEmployee(id);

    }

}

