package com.restful_api.repositories;

import com.restful_api.models.EmployeeView;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeViewRepository extends PagingAndSortingRepository<EmployeeView, Long> {
    List<EmployeeView> findAll(Specification<EmployeeView> specification);

    Optional<EmployeeView> findAll();
    Optional<EmployeeView> findById(Long id);
    boolean existsById(Long id);
    int count();
}
