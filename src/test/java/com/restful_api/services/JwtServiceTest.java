package com.restful_api.services;

import com.restful_api.services.implementations.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtServiceTest {

    // Replace this with the actual secret key for your application
    private static final String SECRET_KEY = "3D42F4A9CEEB41DEB13C69BC0B8B3B6FFD3B3F253D0A923B56CA879E1F083FAA";

    private JwtService jwtService = new JwtService(SECRET_KEY);

    @Test
    public void testGenerateTokenAndValidate() {
        UserDetails userDetails = createDummyUserDetails();
        String token = jwtService.generateToken(userDetails);

        // Assuming userDetails.getUsername() returns "testuser"
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testuser", extractedUsername);

        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isTokenValid);
    }

    private UserDetails createDummyUserDetails() {
        // Implement a UserDetails instance with dummy data for testing
        // Replace this with your actual UserDetails implementation
        return new UserDetails() {
            @Override
            public String getUsername() {
                return "testuser";
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            // Implement other UserDetails methods
            @Override
            public String getPassword() {
                return "testpassword";
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
