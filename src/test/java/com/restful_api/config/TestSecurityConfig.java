package com.restful_api.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@ComponentScan("com.hanzoy.utils")
public class TestSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Define a mock user for testing
        UserDetails user = User.builder()
                .username("testuser")
                .password(passwordEncoder().encode("testpassword"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authentication -> authentication;
    }
}
