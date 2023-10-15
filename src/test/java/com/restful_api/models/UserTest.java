package com.restful_api.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserValidation(){
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<User> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(4, errorNo);
    }

    @Test
    public void testUserValidationPass(){
        User user = new User();
        user.setName("Mangut Innocent");
        user.setEmail("mangut@gmail.com");
        user.setPhone("09098987878");
        user.setPassword("password");
        user.setRole(Role.USER);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidEmailTest(){
        User user = new User();
        user.setName("Mangut Innocent");
        user.setEmail("mangut.com");
        user.setPhone("09098987878");
        user.setPassword("password");
        user.setRole(Role.USER);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<User> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(1, errorNo);
    }

    @Test
    public void testGetAuthorities() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.USER.name())));
    }

    @Test
    public void testGetUsername() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        String username = user.getUsername();

        assertEquals("user@example.com", username);
    }



    @Test
    public void testIsAccountNonExpired() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        boolean isAccountNonExpired = user.isAccountNonExpired();

        assertTrue(isAccountNonExpired);
    }

    @Test
    public void testIsAccountNonLocked() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        boolean isAccountNonLocked = user.isAccountNonLocked();

        assertTrue(isAccountNonLocked);
    }

    @Test
    public void testIsCredentialsNonExpired() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        boolean isCredentialsNonExpired = user.isCredentialsNonExpired();

        assertTrue(isCredentialsNonExpired);
    }

    @Test
    public void testIsEnabled() {
        User user = createUserWithRole("testuser", "user@example.com", "123456", Role.USER);

        boolean isEnabled = user.isEnabled();

        assertTrue(isEnabled);
    }

    private User createUserWithRole(String name, String email, String password, Role role) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}

