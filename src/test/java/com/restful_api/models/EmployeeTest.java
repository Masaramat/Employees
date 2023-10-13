package com.restful_api.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validEmployeeTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setEmail("mangut@gmail.com");
        employee.setPhone("09023454567");
        employee.setAddress("Jan Kwano");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertTrue(violations.isEmpty());

        assertEquals(0, violations.size());

    }

    @Test
    public void invalidAllViolationTest(){
        Employee employee = new Employee();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(4, errorNo);

    }

    @Test
    public void noEmailTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setPhone("09023454567");
        employee.setAddress("Jan Kwano");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(1, errorNo);
    }

    @Test
    public void invalidEmailTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setEmail("mangutsemail");
        employee.setPhone("09023454567");
        employee.setAddress("Jan Kwano");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(1, errorNo);
    }

    @Test
    public void invalidPhoneTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setEmail("mangut@gmail.com");
        employee.setAddress("Jan Kwano");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(1, errorNo);
    }

    @Test
    public void invalidAddressTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setEmail("mangut@gmail.com");
        employee.setPhone("09023454567");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }
        assertEquals(1, errorNo);
    }

    @Test
    public void invalidNameTest(){
        Department department = new Department(1l, "IT", "Computer stuff");
        Employee employee = new Employee();
        employee.setEmail("mangut@gmail.com");
        employee.setPhone("09023454567");
        employee.setAddress("Jan Kwano");
        employee.setDepartment(department);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<Employee> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }
        assertEquals(1, errorNo);
    }


}
