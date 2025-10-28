package com.ali.restaurant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}")
    private String issuerUri;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET,"/api/photos/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()
                                ))

                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable());
        return http.build();

    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        return new JwtAuthenticationConverter();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        if (issuerUri != null && !issuerUri.isEmpty()) {
            return NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
        } else {
            // For development/testing - you can replace this with a proper JWT decoder
            // This is a placeholder that will cause the app to fail if no issuer URI is provided
            throw new IllegalStateException("JWT issuer URI must be configured. " +
                    "Please set spring.security.oauth2.resourceserver.jwt.issuer-uri in application.properties");
        }
    }

}
