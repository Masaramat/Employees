package com.restful_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.restful_api.controllers.EmployeeController;
import com.restful_api.models.Department;
import com.restful_api.models.Employee;
import com.restful_api.models.EmployeeView;
import com.restful_api.repositories.DepartmentRepository;
import com.restful_api.repositories.EmployeeRepository;
import com.restful_api.repositories.EmployeeViewRepository;
import com.restful_api.services.JwtService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = "logging.level.org.springframework.security=DEBUG")
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
    private static String BASE_URL = "/api/v1";
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper;
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock private EmployeeViewRepository viewRepository;
    @Mock private DepartmentRepository departmentRepository;

    @InjectMocks private EmployeeController employeeController;

    EmployeeView employee_1 = new EmployeeView(
            1,
            "Mangut Innocent",
            "innocent@gmail.com",
            "09087676544",
            "ICT",
            "Jankwano",
            1);
    EmployeeView employee_2 = new EmployeeView(
            1,
            "Mangut Innocent",
            "innocent@gmail.com",
            "09087676544",
            "ICT",
            "Terminus",
            1);

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "mangut@gmal.com", roles = "USER")

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void AddEmployee400ErrorTest() throws Exception {
        Department mockDepartment = new Department();
        mockDepartment.setId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(mockDepartment));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "mangut@gmail.com",
                null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = new Employee();
        employee.setName("");
        employee.setAddress("");
        employee.setEmail("");
        employee.setPhone("");
        employee.setDepartment_id(1);
        String requestBody = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post(BASE_URL+"/employee/create").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(400));

    }
    @Test
    public void AddEmployeeSuccessTest() throws Exception {
        Department mockDepartment = new Department();
        mockDepartment.setId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(mockDepartment));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "mangut@gmail.com",
                null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = new Employee();
        employee.setName("Mangut Innocent");
        employee.setAddress("Jan Kwano");
        employee.setEmail("abc@cde.com");
        employee.setPhone("09089765654");
        employee.setDepartment_id(1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        String requestBody = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post(BASE_URL+"/employee/create").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(201));

    }

}
