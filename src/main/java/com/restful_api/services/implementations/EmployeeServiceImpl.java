package com.restful_api.services.implementations;

import com.restful_api.models.Employee;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.services.interfaces.EmployeeService;
import com.restful_api.specifications.EmployeeSpecification;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private  final EmployeeRepository employeeRepository;
    @Override
    public ResponseEntity<?> createEmployee(Employee employee) {
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

    @Override
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        // when there is no employee in the table
        if (employees.isEmpty()){
            return ResponseEntity.status(204).body("No employee found");
        }else {
            return ResponseEntity.ok(employees);
        }
    }

    @Override
    public ResponseEntity<?> getPaginatedEmployees(int page, int size) {
        // Using Page to get list of employees with certain number on a page
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(page, size));
        if(employeePage.isEmpty()){
            return ResponseEntity.status(404).body("No employee found");
        }
        return ResponseEntity.ok().body(employeePage);// Using Page to get list of employees with certain number on a page

    }

    @Override
    public ResponseEntity<?> getSortedEmployees(String sortBy, String sortType) {
        Sort sort = Sort.by(sortBy);
        if(Objects.equals(sortType, "asc")){
            sort = Sort.by(sortBy).ascending();
        } else if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortBy).descending();
        }
        return ResponseEntity.ok().body(employeeRepository.findAll(sort));
    }

    @Override
    public ResponseEntity<?> getEmployee(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new UsernameNotFoundException("Employee does not exist");
        }else {
            return ResponseEntity.ok().body(employeeRepository.findById(id));
        }

    }

    @Override
    public List<Employee> searchEmployees(String attribute, String value) {
        Specification<Employee> specification = EmployeeSpecification.searchEmployee(attribute, value);
        return employeeRepository.findAll(specification);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) throws Exception {
        try{
            // Checks if the employee to be updated exists in the database
            if(!employeeRepository.existsById(id)){
                // sets status code to not found
                throw new UsernameNotFoundException("User not found");
            }
            Optional<Employee> updateEmployee = employeeRepository.findById(id);

            updateEmployee.get().setDepartment(employee.getDepartment());
            updateEmployee.get().setName(employee.getName());
            updateEmployee.get().setEmail(employee.getEmail());
            updateEmployee.get().setPhone(employee.getPhone());
            updateEmployee.get().setAddress(employee.getAddress());
            updateEmployee.get().setDepartment(employee.getDepartment());

            employeeRepository.save(updateEmployee.get());
            // success returns 200 status and the updated employee
            return ResponseEntity.ok().body(updateEmployee.get());

        }catch (DataIntegrityViolationException ex){
            // Throws exception to be caught by ExceptionHandler class
            throw new DataIntegrityViolationException("Duplicate email or phone");
        }catch (Exception ex){
            throw new Exception("Unknown error");
        }

    }

    @Override
    public ResponseEntity<?> deleteEmployee(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new UsernameNotFoundException("Employee does not exist");

        }
        Optional<Employee> deletedEmployee;
        deletedEmployee = employeeRepository.findById(id);

        employeeRepository.deleteById(id);
        return ResponseEntity.status(204).body(deletedEmployee.get().getName() + " deleted successfully" );

    }
}
