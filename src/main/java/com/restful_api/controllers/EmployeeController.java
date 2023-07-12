package com.restful_api.controllers;

import com.restful_api.models.Department;
import com.restful_api.models.Employee;
import com.restful_api.repositories.DepartmentRepository;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.specifications.EmployeeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
//Most or all logic are handled here because extensive logic is not required by the APIs
@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeController() {
    }

    @PostMapping(value = "/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        // try because of to catch database errors
        try {
            // created employee saved to the database
            Employee savedEmployee = employeeRepository.save(employee);
            if (savedEmployee.getName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create employee");

            }
            return ResponseEntity.status(201).body(savedEmployee);
        }catch (DataIntegrityViolationException ex){
            // throws exception if data integrity is violated
            throw new DataIntegrityViolationException("Department does not exist or duplicate email/phone");
        }

    }
    // Gets all employees
    @GetMapping(value = "/employees")
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        // when there is no employee in the table
        if (employees.isEmpty()){
            return ResponseEntity.status(204).body("No employee found");
        }else {
            return ResponseEntity.ok(employees);
        }
    }
    @GetMapping(value = "/employees/page/{page}")
    public ResponseEntity<?> GetPaginatedEmployees(@PathVariable int page){
        // Using Page to get list of employees with certain number on a page
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(page, 2));
        if(employeePage.isEmpty()){
            return ResponseEntity.status(204).body("No employee found");
        }
        return ResponseEntity.ok().body(employeePage);
    }

    // Gets list of employees and sort by given parameter
    @GetMapping(value = "/employees/sort/{sortBy}/{sortType}")
    public ResponseEntity<?> getSortedEmployees(
            @PathVariable String sortBy,
            @PathVariable String sortType){
        Sort sort = Sort.by(sortBy);
        if(Objects.equals(sortType, "asc")){
            sort = Sort.by(sortBy).ascending();
        } else if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortBy).descending();
        }
        return ResponseEntity.ok().body(employeeRepository.findAll(sort));
    }


    // Gets an employee by id
    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Integer id){

        if(!employeeRepository.existsById(id)){
          return ResponseEntity.badRequest().body("Employee does not exist");
        }
        return ResponseEntity.ok().body(employeeRepository.findById(id));
    }
    // Implements search employee by any attribute. this uses the Like keyword so you can implement dynamic search
    // on your UI
    //Posible to use Pagination to implement a simple form of this request
    @GetMapping(value = "/employees/search/{attribute}/{value}")
    public List<Employee> searchEmployees(
            @PathVariable("attribute") String attribute,
            @PathVariable("value") String value) {
        Specification<Employee> specification = EmployeeSpecification.searchEmployee(attribute, value);
        return employeeRepository.findAll(specification);
    }

    // Update employee with certain ID
    @PutMapping(value = "/employee/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) throws Exception {
        try{
            // Checks if the employee to be updated exists in the database
            if(!employeeRepository.existsById(id)){
                // sets status code to not found
                return ResponseEntity.status(404).body("Employee not found");
            }
            Employee updateEmployee = employeeRepository.findById(id);

            updateEmployee.setDepartment(employee.getDepartment());
            updateEmployee.setName(employee.getName());
            updateEmployee.setEmail(employee.getEmail());
            updateEmployee.setPhone(employee.getPhone());
            updateEmployee.setAddress(employee.getAddress());
            updateEmployee.setDepartment(employee.getDepartment());

            employeeRepository.save(updateEmployee);
            // success returns 200 status and the updated employee
            return ResponseEntity.ok().body(updateEmployee);

        }catch (DataIntegrityViolationException ex){
            // Throws exception to be caught by ExceptionHandler class
            throw new DataIntegrityViolationException("Duplicate email or phone");
        }catch (Exception ex){
            throw new Exception("Unknown error");
        }



    }

    @DeleteMapping(value = "employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id){
        if(!employeeRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee does not exist");
        }
        Employee deletedEmployee = employeeRepository.findById(id);

        employeeRepository.deleteById(id);
        return ResponseEntity.status(204).body(deletedEmployee.getName() + " deleted successfully" );


    }

}

