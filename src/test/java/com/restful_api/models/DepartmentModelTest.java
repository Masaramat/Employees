package com.restful_api.models;

import com.restful_api.models.Department;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentModelTest {
    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validDepartmentTest(){
        Department department = new Department(1l, "ICT", "Anything");

        Set<ConstraintViolation<Department>> violations = validator.validate(department);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidDepartmentTest(){
        Department department = new Department();
        department.setDescription("Something");

        Set<ConstraintViolation<Department>> violations = validator.validate(department);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        int errorNo = 0;

        for(ConstraintViolation<Department> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            if(errorNo == 1){
                assertEquals("Name is required", errorMessage);
            }
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }
    }
}
