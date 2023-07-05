package com.restful_api.configurations;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable());
                http.securityMatchers((matchers)->matchers
                        .requestMatchers("/api/auth"))
                        .authorizeHttpRequests((authorize)->authorize
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .anyRequest().hasRole("USER"))

                                .anonymous((anonymous)->anonymous.disable())
                        .sessionManagement((session)->session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
                http.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                        .logout(logout->logout
                                .logoutUrl("/auth/logout")
                                .invalidateHttpSession(true));



        return http.build();
    }
}
