package com.restful_api.services.interfaces;

import com.restful_api.models.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EmployeeService {

    public ResponseEntity<?> createEmployee(Employee employee);

    public ResponseEntity<?> getAllEmployees();

    public ResponseEntity<?> getPaginatedEmployees(int page, int size);

    public ResponseEntity<?> getSortedEmployees(String sortBy, String sortType);

    public ResponseEntity<?> getEmployee(Long id);

    public List<Employee> searchEmployees(String attribute, String value);
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) throws Exception;



    public ResponseEntity<?> deleteEmployee(Long id);
}
