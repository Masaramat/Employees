package com.restful_api.repositories;

import com.restful_api.models.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    List<Employee> findAll(Specification<Employee> specification);
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    List<Employee> findAll();

    Employee save(Employee employee);
    Employee findById(int id);

    boolean existsById(int id);

    Employee deleteById(int id);

}

