package com.restful_api.repositories;

import com.restful_api.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, CrudRepository<Employee, Long> {
    List<Employee> findAll(Specification<Employee> specification);


}

