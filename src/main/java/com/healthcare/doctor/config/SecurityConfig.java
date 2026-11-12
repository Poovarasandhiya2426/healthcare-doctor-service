
package com.healthcare.doctor.config;

import com.healthcare.doctor.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // OPTIONS requests
                        .requestMatchers(
                                HttpMethod.OPTIONS, "/**"
                        ).permitAll()

                        // Public actuator endpoints
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/info"
                        ).permitAll()

                        // Doctor registration - ADMIN only
                        .requestMatchers(
                                HttpMethod.POST, "/api/doctors"
                        ).hasAuthority("ROLE_ADMIN")

                        // Doctor update - ADMIN only
                        .requestMatchers(
                                HttpMethod.PUT, "/api/doctors/**"
                        ).hasAuthority("ROLE_ADMIN")

                        // Doctor deactivation - ADMIN only
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/doctors/*/deactivate"
                        ).hasAuthority("ROLE_ADMIN")

                        // Doctor read operations
                        .requestMatchers(
                                HttpMethod.GET, "/api/doctors/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR",
                                "ROLE_RECEPTIONIST"
                        )

                        // Specialization creation - ADMIN only
                        .requestMatchers(
                                HttpMethod.POST, "/api/specializations"
                        ).hasAuthority("ROLE_ADMIN")

                        // Specialization update - ADMIN only
                        .requestMatchers(
                                HttpMethod.PUT, "/api/specializations/**"
                        ).hasAuthority("ROLE_ADMIN")

                        // Specialization deactivation - ADMIN only
                        .requestMatchers(
                                HttpMethod.PATCH, "/api/specializations/**"
                        ).hasAuthority("ROLE_ADMIN")

                        // Specialization read operations
                        .requestMatchers(
                                HttpMethod.GET, "/api/specializations/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR",
                                "ROLE_RECEPTIONIST"
                        )

                        // Availability creation - ADMIN or DOCTOR
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/doctor-availability/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR"
                        )

                        // Availability update - ADMIN or DOCTOR
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/doctor-availability/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR"
                        )

                        // Availability deactivation - ADMIN or DOCTOR
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/doctor-availability/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR"
                        )

                        // Availability read operations
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/doctor-availability/**"
                        ).hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR",
                                "ROLE_RECEPTIONIST"
                        )

                        // All remaining requests require authentication
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
