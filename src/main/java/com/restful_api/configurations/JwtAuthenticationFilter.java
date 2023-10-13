package com.restful_api.configurations;

import com.restful_api.services.implementations.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
// extends OncePerRequestFilter from spring web filter
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;
    // Performs filter for authentication
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            // Throws exceptions to be caught by RestExceptionHandler class
    ) throws ServletException, IOException, SignatureException, MalformedJwtException, ExpiredJwtException {
        try{
            // Makes sure authentication request is from authentication URLs
            if (request.getServletPath().contains("/api/v1/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            // Extracts authentication header
            final String authHeader = request.getHeader("Authorization");

            final String jwt;
            final String userEmail;
            // Making sure the Authentication header is of appropriate format
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            // extracts the jwt token from the end of Bearer
            jwt = authHeader.substring(7);

            // Extracts username from token and performs authorization
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        }catch (ExpiredJwtException | SignatureException | MalformedJwtException ex){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Unauthorized: " + ex.getMessage());
        }catch (ServletException | IOException ex){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error: " + ex.getMessage());

        }

    }
}
