
package com.healthcare.doctor.config;

import com.healthcare.doctor.security.JwtAuthenticationFilter;
import com.healthcare.doctor.security.RestAccessDeniedHandler;
import com.healthcare.doctor.security.RestAuthenticationEntryPoint;

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
        private final RestAuthenticationEntryPoint authenticationEntryPoint;
        private final RestAccessDeniedHandler accessDeniedHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

                http
                        // Disable CSRF for stateless REST APIs
                        .csrf(csrf -> csrf.disable())

                        // Stateless session management
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )

                        // Authentication and authorization exception handling
                        .exceptionHandling(exception -> exception
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                        )

                        // Authorization rules
                        .authorizeHttpRequests(auth -> auth

                                // Allow OPTIONS requests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                // Public actuator endpoints
                                .requestMatchers(
                                        "/actuator/health",
                                        "/actuator/info"
                                ).permitAll()

                                // Swagger and OpenAPI endpoints
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                // =====================================
                                // DOCTOR APIs
                                // =====================================

                                // Register doctor - ADMIN only
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/doctors"
                                ).hasAuthority("ROLE_ADMIN")

                                // Update doctor - ADMIN or DOCTOR
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/doctors/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR"
                                )

                                // Deactivate doctor - ADMIN only
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/doctors/*/deactivate"
                                ).hasAuthority("ROLE_ADMIN")

                                // Read doctor details
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/doctors/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR",
                                        "ROLE_RECEPTIONIST"
                                )

                                // =====================================
                                // SPECIALIZATION APIs
                                // =====================================

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/specializations"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/specializations/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/specializations/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/specializations/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/specializations/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR",
                                        "ROLE_RECEPTIONIST"
                                )

                                // =====================================
                                // DOCTOR AVAILABILITY APIs
                                // =====================================

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/doctor-availability/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR"
                                )

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/doctor-availability/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR"
                                )

                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/doctor-availability/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR"
                                )

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/doctor-availability/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/doctor-availability/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR",
                                        "ROLE_RECEPTIONIST"
                                )

                                // =====================================
                                // DOCTOR QUALIFICATION APIs
                                // =====================================

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/doctor-qualifications"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/doctor-qualifications/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/doctor-qualifications/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/doctor-qualifications/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR",
                                        "ROLE_RECEPTIONIST"
                                )

                                // =====================================
                                // DOCTOR HOSPITAL MAPPING APIs
                                // =====================================

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/doctor-hospitals"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/doctor-hospitals/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/doctor-hospitals/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/doctor-hospitals/**"
                                ).hasAuthority("ROLE_ADMIN")

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/doctor-hospitals/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN",
                                        "ROLE_DOCTOR",
                                        "ROLE_RECEPTIONIST"
                                )

                                // Deny all unmatched endpoints
                                .anyRequest().denyAll()
                        )

                        // Register JWT filter
                        .addFilterBefore(
                                jwtAuthenticationFilter,
                                UsernamePasswordAuthenticationFilter.class
                        );

                return http.build();
        }
}
