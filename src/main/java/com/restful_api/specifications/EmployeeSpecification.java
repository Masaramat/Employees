package com.restful_api.specifications;
import com.restful_api.models.EmployeeView;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class EmployeeSpecification {
    public static Specification<EmployeeView> searchEmployee(String attribute, String value) {
        return new Specification<EmployeeView>() {
            @Override
            public Predicate toPredicate(Root<EmployeeView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(attribute), "%" + value + "%");
            }
        };
    }
}
