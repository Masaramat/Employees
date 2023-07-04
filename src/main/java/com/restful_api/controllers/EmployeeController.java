package com.restful_api.controllers;

import com.restful_api.models.Employee;
import com.restful_api.models.EmployeeView;
import com.restful_api.repositories.DepartmentRepository;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.repositories.EmployeeViewRepository;
import com.restful_api.specifications.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//Most or all logic are handled here because extensive logic is not required by the APIs
@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeViewRepository employeeViewRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeController() {
    }

    @PostMapping(value = "/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        // Check if department sent with the request exist
        if (departmentRepository.findById(employee.getDepartment_id()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Selected department does not exist");
        }
        if(employeeRepository.existsByEmail(employee.getEmail()) || employeeRepository.existsByPhone(employee.getPhone())){
            throw new DataIntegrityViolationException("Duplicate email or phone number");
        }
        // created employee saved to the database
        Employee savedEmployee = employeeRepository.save(employee);
        if (savedEmployee.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create employee");

        }
        return ResponseEntity.status(201).body(savedEmployee);

    }
    @GetMapping(value = "/employees")
    public ResponseEntity<?> getAllEmployees(){
        List<EmployeeView> employees = employeeViewRepository.findAll();
        // when there is no employee in the table
        if (employees.isEmpty()){
            return ResponseEntity.status(204).body("No employee found");
        }else {
            return ResponseEntity.ok(employees);
        }
    }
    @GetMapping(value = "/employees/page")
    public ResponseEntity<?> GetPaginatedEmployees(@RequestParam("page") int page){
        if(employeeViewRepository.count() <= 0){
            return ResponseEntity.status(204).body("No employee found");
        }
        return ResponseEntity.ok().body(employeeViewRepository.findAll(PageRequest.of(page, 5)));
    }

    @GetMapping(value = "/employees/sort/{sortBy}/{sortType}")
    public ResponseEntity<?> getSortedEmployees(
            @PathVariable String sortBy,
            @PathVariable String sortType){
        Sort sort = Sort.by(sortBy);
        if(sortType == "asc"){
            sort = Sort.by(sortBy).ascending();
        } else if (sortType == "desc") {
            sort = Sort.by(sortBy).descending();
        }
        return ResponseEntity.ok().body(employeeViewRepository.findAll(sort));
    }



    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        if(!employeeViewRepository.existsById(id)){
          return ResponseEntity.badRequest().body("Employee does not exist");
        }
        return ResponseEntity.ok().body(employeeViewRepository.findById(id));
    }
    // Implements search employee by any attribute. this uses the Like keyword so you can implement dynamic search
    // on your UI
    //Posible to use Pagination to implement a simple form of this request
    @GetMapping(value = "/employees/search/{attribute}/{value}")
    public List<EmployeeView> searchEmployees(
            @PathVariable("attribute") String attribute,
            @PathVariable("value") String value) {
        Specification<EmployeeView> specification = EmployeeSpecification.searchEmployee(attribute, value);
        return employeeViewRepository.findAll(specification);
    }



    @PutMapping(value = "/employee/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        // Checks if the employee to be updated exists in the database
        if(!employeeRepository.existsById(id)){
            // sets status code to not found
            return ResponseEntity.status(404).body("user not found");
        }
        Employee updateEmployee = employeeRepository.findById(id).get();

            updateEmployee.setDepartment_id(employee.getDepartment_id());
            updateEmployee.setName(employee.getName());
            updateEmployee.setEmail(employee.getEmail());
            updateEmployee.setPhone(employee.getPhone());
            updateEmployee.setAddress(employee.getAddress());
        // Case where an unknown error occurs
        if(employeeRepository.save(updateEmployee).getName() == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update employee");
        }
        // success returns 200 status and the updated employee
        return ResponseEntity.ok().body(updateEmployee);

    }

    @DeleteMapping(value = "employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if(!employeeRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }
        Optional<Employee> deletedEmployee = employeeRepository.findById(id);

        employeeRepository.deleteById(id);
        return ResponseEntity.status(204).body(deletedEmployee.get().getName() + " deleted successfully" );


    }

}

