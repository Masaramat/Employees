package com.restful_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityTestConfig {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername("testuser")
                .password("{noop}testpassword") // Use {noop} for plain text password (for testing only)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
