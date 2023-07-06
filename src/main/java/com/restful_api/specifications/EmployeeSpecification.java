package com.restful_api.specifications;
import com.restful_api.models.Employee;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class EmployeeSpecification {
    public static Specification<Employee> searchEmployee(String attribute, String value) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(attribute), "%" + value + "%");
            }
        };
    }
}
