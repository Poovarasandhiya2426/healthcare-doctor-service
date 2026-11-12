
package com.healthcare.doctor.security;

public record DoctorUserPrincipal(
        Long userId,
        String email
) {
}
