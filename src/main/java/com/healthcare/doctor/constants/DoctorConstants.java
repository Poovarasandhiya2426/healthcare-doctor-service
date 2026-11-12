
package com.healthcare.doctor.constants;

public final class DoctorConstants {

    private DoctorConstants() {
        // Prevent object creation
    }

    // Doctor status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    // Success messages
    public static final String DOCTOR_REGISTERED =
            "Doctor registered successfully";

    public static final String DOCTOR_FETCHED =
            "Doctor details fetched successfully";

    public static final String DOCTORS_FETCHED =
            "Doctor list fetched successfully";

    public static final String DOCTOR_UPDATED =
            "Doctor details updated successfully";

    public static final String DOCTOR_DEACTIVATED =
            "Doctor deactivated successfully";

    // Error messages
    public static final String DOCTOR_NOT_FOUND =
            "Doctor not found";

    public static final String DUPLICATE_EMAIL =
            "Doctor email already exists";

    public static final String DUPLICATE_PHONE =
            "Doctor phone number already exists";

    public static final String DUPLICATE_LICENSE =
            "Doctor license number already exists";

    public static final String INVALID_DOCTOR_ID =
            "Valid doctor ID is required";

    public static final String INVALID_SEARCH_KEYWORD =
            "Search keyword cannot be empty";

    public static final String INVALID_DOCTOR_STATUS =
            "Invalid doctor status. Use ACTIVE or INACTIVE";

    public static final String DOCTOR_ALREADY_INACTIVE =
            "Doctor is already inactive";

    public static final String IDENTITY_USER_ALREADY_LINKED =
            "Identity user is already linked to a doctor";

    public static final String INVALID_SPECIALIZATION_ID =
            "Valid specialization ID is required";

    public static final String DOCTOR_STATUS_REQUIRED =
            "Doctor status is required";
}
