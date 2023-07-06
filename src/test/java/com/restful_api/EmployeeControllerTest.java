package com.restful_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful_api.controllers.EmployeeController;
import com.restful_api.models.Department;
import com.restful_api.models.Employee;
import com.restful_api.repositories.DepartmentRepository;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.services.JwtService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = "logging.level.org.springframework.security=DEBUG")
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
    private static String BASE_URL = "/api/v1";
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock private DepartmentRepository departmentRepository;

    @InjectMocks private EmployeeController employeeController;

//    Creating mock employees
    Employee employee_1 = new Employee();
    Employee employee_2 = new Employee();

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "mangut@gmal.com", roles = "USER")

    // Setup to initialize Mockito and objectMapper
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        objectMapper = Mockito.mock(ObjectMapper.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }
    // Test for add employee with bad request
    @Test
    public void AddEmployee400ErrorTest() throws Exception {
        Department mockDepartment = new Department();
        mockDepartment.setId(1);
        when(departmentRepository.findById(1)).thenReturn(mockDepartment);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "mangut@gmail.com",
                null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        // Creating a bad request with Employee object
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = new Employee();
        employee.setName("");
        employee.setAddress("");
        employee.setEmail("");
        employee.setPhone("");
        employee.setDepartment(mockDepartment);
        String requestBody = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post(BASE_URL+"/employee/create").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(400));

    }

    //test for add employee success
    @Test
    public void AddEmployeeSuccessTest() throws Exception {
        Department mockDepartment = new Department();
        mockDepartment.setId(1);
        when(departmentRepository.findById(1)).thenReturn(mockDepartment);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "mangut@gmail.com",
                null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //in order to pass the test, Always change the values for phone and email to avoid errors
        //Make sure the department ID you supply, exists in the database
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setAddress("Jan Kwano");
        employee.setEmail("csc@cde.com");
        employee.setPhone("0080965654");
        employee.setDepartment(mockDepartment);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        String requestBody = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post(BASE_URL+"/employee/create").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(201));

    }
    // Test for get all employee success
    @Test
    public void getEmployeesSuccessTest() throws Exception {
        // Populating mock data for fetching
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee_1);
        employeeList.add(employee_2);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        mockMvc.perform(get(BASE_URL+"/employees"))
                .andExpect(status().is(200));
    }



}
