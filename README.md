# Java: Employee management api

## Overview
This project is an interview project to create an employee management API to perform simple
CRUD Operations on the employee table and departments table

## Guidelines
To run the project you need to have MySql and MySQL workbench
Have JDK running on your computer
Clone the project into a directory and open with an IDE

## Source Code Review
This project is composed of the MVC Architecture and needs only little work to configure



1. Import the project and Navigate to the application.properties file. This is where the application setup is:
   Adjust the settings to fit your mysql settings
```xml
    spring.datasource.url=jdbc:mysql://localhost:3309/employees_db
        spring.datasource.driver-class-name=com.mysql.jdbc.Driver
        spring.datasource.username=your_username
        spring.datasource.password=Your_password
        spring.jpa.hibernate.dll-auto=none
        spring.h2.console.enabled=true
```
2. Go to the pom.xml file and reload the project to download dependencies
2. The models are contained in the Employee.java and Department.java files and to
   Make authentication work I included User.java all this files contain table information
```java
    @Entity
    @Table(name = "departments", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
    @Data
    public class Department {
    //class code
    }
    
    @Entity
    @Table(name = "employees", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "phone"})})
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    public class Employee {
        //class code
    }

    @Entity
    @Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User implements UserDetails {
        //class code
    }
```
Other class is the employee view which extends employee to create a join between employee and department table
3. Sring security is used for authentication and authorization and the classes that perform that task
   are the ApplicationConfig.java, JwtAuthenticationFilter and SecurityConfiguration

4. The controllers control the logic of the application, they contain the routes and the various logic each route performs
   The major controller as far as this exercise in concerned is the EmployeeController.java which handles all CRUD
   operations on the employee table EX:
```java
    @RestController
    @RequestMapping(value = "/api/v1")
    public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeViewRepository employeeViewRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeController() {
    }

    @PostMapping(value = "/employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        //logic for the create employee api
    }

}


```

## Running and testing the api
1. open the RestApplication.java which contains the main method and run it
2. Once you run the program Luiquibase should create your database migrations and migrate them
3. If you expirience problem with the migrations start testing the API by Post data APIs
3. it should show you your application started and give you the port information. if you encounter an error
   like the port is in use; you can go to the application.properties file and change your port number
```xml
    server.port:9000 
```

Changes your port to 9000 you can use any port number.
4. Once your application is running you can test you application on POSTMAN or Any
   API Testing platform

## APIs and their properties
When testing on localhost your apis should be as follow
1. register (POST) API
   http://localhost:8080/api/auth/register
   Header:
   Application/json
   -request body:
    ```json
        {
            "name": "name",
            "email": "email@mail.com",
            "phone": "09098778766",
            "password": "password"
        }
   
    ```
2. login (POST) API
   http://localhost:8080/api/auth/login
   Header:
   Application/json
   -request body:
    ```json
        {            
            "email": "email@mail.com",
            "password": "password"
        }
   
    ```
3. Create employee (POST) API
   Header:
   Application/json
   -request body:
    ```json
        {
            "name": "name",
            "email": "email@mail.com",
            "phone": "09098778766",
            "address": "address",
            "department_id": 1
        }
   
    ```
4. Fetch all employees (GET) API
   http://localhost:8080/api/v1/employees
5. Fetch employees with pagination (GET) API
   http://localhost:8080/api/v1/employees/page
   replace page with the page number
6. Fetch employee and sort by (GET) API
   http://localhost:8080/api/v1/employees/sort/{sortBy}/{sortType} (replace {sortBy} with the column you want and {sortType} as asc or desc)

7. Get a single employee by Id (GET) API
   http://localhost:8080/api/v1/employee/employee/{id}

8. Filter employee search by a certain attribute (GET) API
   http://localhost:8080/api/v1/employees/search/{attribute}/{value}

9. Update employee with certain ID (PUT) API
   http://localhost:8080/api/v1/employee/update/{id}
   Header:
   Application/json
   -request body:
    ```json
        {
            "name": "name",
            "email": "email@mail.com",
            "phone": "09098778766",
            "address": "address",
            "department_id": 1
        }
   
    ```

10. Delete employee with certain ID (DELETE) API
    http://localhost:8080/api/v1/employee/delete/{id}

11. Get all departments (GET) API
    http://localhost:8080/api/v1/departments

12. Create a department (POST) API
    http://localhost:8080/api/v1/department/create
    Header:
    Application/json
    -request body:
    ```json
        {
            "name": "name",
            "description": "description"
        }

    ```

13. Delete a department (DELETE) API
    http://localhost:8080/api/v1/department/delete/{id}

## More Info


## Disclaimers
1. Currently, the authentication system is not working as expected It allows some APIs it is supposed to authenticate
   even though the login and token system actually works fine
2. Since authentication is blocking my test classes from executing, I had to disable authentication in the test sequence
3. Still working on better table joins
4. Trying to learn better testing technologies (using docker and containers)
5. Liquibase is active but have minor contriants conflict with JPA. I am still working on it
6. You might have a bit of problem with adding first elements (Keep adding againg as this is going to be resseting the seq tables)

## Dependencies and versions
1. Spring Boot version: 3.1.1
2. spring-boot-starter-actuator
3. spring-boot-starter-data-jpa (For Data aageent)
4. spring-boot-starter-web
5. liquibase-core (For database migrations management)
6. com.h2database
7. mysql-connector-j runtime (MySQL Connectivity)
8. spring-boot-starter-test (Spring test)
9. lombok (Logging)
10. jjwt-api 0.11.5 (Security support)
11. jjwt-impl 0.11.5
12. jjwt-jackson 0.11.5
13. springdoc-openapi-starter-webmvc-ui 2.1.0
14. spring-boot-starter-validation (Security:: Validation)
15. spring-security-test (Security test)
16. spring-boot-starter-security (Secrity)
17. spring-security-core (Security)
18. spring-security-web
19. junit 4.13.2 (Unit testing)
20. modelmapper 3.1.1

