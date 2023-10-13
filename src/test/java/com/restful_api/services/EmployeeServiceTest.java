package com.restful_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.restful_api.services.implementations.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collection;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceTest {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String SECRET_KEY = "3D42F4A9CEEB41DEB13C69BC0B8B3B6FFD3B3F253D0A923B56CA879E1F083FAA";

    private final JwtService jwtService = new JwtService(SECRET_KEY);

    private final ObjectMapper objectMapper = new ObjectMapper();

    ArrayNode jsonArray = objectMapper.createArrayNode();

    @Test
    public void getEmployeeSuccessTest() {
//        UserDetails userDetails = readUserDetailsFromJsonFile("user_test.json");
        UserDetails userDetails = createDummyUserDetails();

        String token = jwtService.generateToken(userDetails);

        // Set the JWT token in the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Create an HttpEntity with the headers
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/employees", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void getEmployeeWithoutAuthTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/employees", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void getEmployeeWithIdSuccessTest() {
//        UserDetails userDetails = readUserDetailsFromJsonFile("user_test.json");
        UserDetails userDetails = createDummyUserDetails();

        String token = jwtService.generateToken(userDetails);

        // Set the JWT token in the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Create an HttpEntity with the headers
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/employee/1", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            // Add the JSON object to the array
            jsonArray.add(jsonNode);

            // Check the size of the array
            int arraySize = jsonArray.size();

            // Check the number of objects returned (assuming it's an array)
            assertEquals(1, arraySize);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to parse the JSON response.");
        }


    }

    @Test
    public void getEmployeeWithPageSuccessTest() {
//        UserDetails userDetails = readUserDetailsFromJsonFile("user_test.json");
        UserDetails userDetails = createDummyUserDetails();

        String token = jwtService.generateToken(userDetails);

        // Set the JWT token in the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Create an HttpEntity with the headers
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/employees/page/1/size/2", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }



//    private UserDetails readUserDetailsFromJsonFile(String jsonFilePath) {
//        try {
//            // Load the JSON file from the classpath
//            Resource resource = new ClassPathResource(jsonFilePath);
//
//            // Read JSON data from the resource
//            String jsonContent = new String(resource.getInputStream().readAllBytes());
//
//            System.out.println("JSON Content: " + jsonContent);
//
//            // Deserialize JSON content into UserDetails
//            return objectMapper.readValue(jsonContent, UserDetails.class);
//        } catch (IOException e) {
//            // Handle file reading or JSON deserialization exception
//            return null;
//        }
//    }

    private UserDetails createDummyUserDetails() {
        // Implement a UserDetails instance with dummy data for testing
        // Replace this with your actual UserDetails implementation
        return new UserDetails() {
            @Override
            public String getUsername() {
                return "new@gmail.com";
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            // Implement other UserDetails methods
            @Override
            public String getPassword() {
                return "password";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }


}
