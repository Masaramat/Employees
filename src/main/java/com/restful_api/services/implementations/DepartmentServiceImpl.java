package com.restful_api.services.implementations;

import com.restful_api.models.Department;
import com.restful_api.repositories.DepartmentRepository;
import com.restful_api.services.interfaces.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    @Override
    public ResponseEntity<?> createDepartment(Department department) {
        Department saved = repository.save(department);
        if (saved.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add department");
        }
        return ResponseEntity.status(201).body(saved);
    }

    @Override
    public ResponseEntity<?> getAllDepartment() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Long id) {
        try{
            if(!repository.existsById(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
            }
            repository.deleteById(id);
            return ResponseEntity.status(204).body("deleted successfully");
        }catch (DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException("Cannot delete department as it is referenced in a table");
        }
    }
}
