
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Stateless session
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Allow OPTIONS requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public actuator endpoints
                        .requestMatchers("/actuator/health", "/actuator/info")
                        .permitAll()

                        // =====================================
                        // DOCTOR APIs
                        // =====================================

                        .requestMatchers(HttpMethod.POST, "/api/doctors")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/doctors/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/doctors/*/deactivate")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/doctors/**")
                        .hasAnyAuthority(
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
                        )
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/specializations/**"
                        )
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/specializations/**"
                        )
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/specializations/**"
                        )
                        .hasAnyAuthority(
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
                        )
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/doctor-availability/**"
                        )
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/doctor-availability/**"
                        )
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/doctor-availability/**"
                        )
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/doctor-availability/**"
                        )
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR",
                                "ROLE_RECEPTIONIST"
                        )

                        // =====================================
                        // DOCTOR QUALIFICATION APIs
                        // =====================================

                        // Create qualification - ADMIN only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/doctor-qualifications"
                        )
                        .hasAuthority("ROLE_ADMIN")

                        // Update qualification - ADMIN only
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/doctor-qualifications/**"
                        )
                        .hasAuthority("ROLE_ADMIN")

                        // Delete qualification - ADMIN only
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/doctor-qualifications/**"
                        )
                        .hasAuthority("ROLE_ADMIN")

                        // Read qualification - ADMIN, DOCTOR, RECEPTIONIST
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/doctor-qualifications/**"
                        )
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_DOCTOR",
                                "ROLE_RECEPTIONIST"
                        )

                        // All remaining APIs require authentication
                        .anyRequest().authenticated()
                )

                // Add JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
