package com.restful_api.services.interfaces;

import com.restful_api.models.Department;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DepartmentService {
    public ResponseEntity<?> createDepartment(Department department);
    public ResponseEntity<?> getAllDepartment();

    public ResponseEntity<?> deleteDepartment(Long id);
}
